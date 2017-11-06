package com.greak.ui.screens.post;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chrono.src.common.constants.StringConstants;
import com.chrono.src.ui.BaseFragment;
import com.greak.R;
import com.greak.common.utils.StatsConstants;
import com.greak.data.database.UserActionsPreferences;
import com.greak.data.models.Post;
import com.greak.data.rest.RestService;
import com.greak.ui.analytics.FabricAnalyticsManager;
import com.greak.ui.common.FragmentCommunicationUtils;
import com.greak.ui.common.ObservableWebView;
import com.greak.ui.common.TimeUtils;
import com.greak.ui.common.resolvers.OnLoginListener;
import com.greak.ui.screens.channel.ChannelActivity;
import com.greak.ui.screens.main.discover.OnFeedRefreshListener;
import com.greak.ui.screens.post.clickhandlers.ObserveHandler;
import com.greak.ui.screens.post.clickhandlers.OnLoginRequired;
import com.greak.ui.screens.post.clickhandlers.VoteHandler;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Filip Kowalski on 26.03.17.
 */

public class PostFragment extends BaseFragment<PostPresenter> implements OnLoginRequired, OnLoginListener {

	private static final String BUNDLE_POST = "post";
	private static final String BUNDLE_SHOW_COVER = "show_cover";

	@BindView(R.id.image_channel_avatar)
	protected ImageView channelAvatar;

	@BindView(R.id.text_name_channel)
	protected TextView channelName;

	@BindView(R.id.text_description_channel)
	protected TextView channelDescription;

	@BindView(R.id.button_observe)
	protected TextView observeButton;

	@BindView(R.id.text_content_post)
	protected ObservableWebView postContent;

	@BindView(R.id.button_comments_post)
	protected TextView commentsCount;

	@BindView(R.id.button_like_post)
	protected TextView likeButton;

	@BindView(R.id.button_give_tip)
	protected TextView giveTip;

	private boolean showCover;
	private boolean finishedScrolling;
	private long readingStartTime;

	private Post post;
	private ObserveHandler observeHandler;
	private VoteHandler voteHandler;
	private SignInSheetViewHolder loginSheetHolder;
	private PostCoverSheetViewHolder coverPhotoHolder;

	private OnFeedRefreshListener feedRefreshListener;

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		feedRefreshListener = FragmentCommunicationUtils.getListenerOrThrow(this, OnFeedRefreshListener.class);
	}

	public static PostFragment newInstance(Post post, boolean showCover) {
		Bundle args = new Bundle();
		args.putParcelable(BUNDLE_POST, post);
		args.putBoolean(BUNDLE_SHOW_COVER, showCover);
		PostFragment fragment = new PostFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
	                         @Nullable Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_post, container, false);
		ButterKnife.bind(this, view);

		initArguments(savedInstanceState);
		initValues();
		initUi();
		initSheets(view);

		FabricAnalyticsManager.logEvent(StatsConstants.POST, post.getTitle());

		return view;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		readingStartTime = System.currentTimeMillis();
	}

	private void initArguments(Bundle savedInstanceState) {
		Bundle extras = savedInstanceState == null ? getArguments() : savedInstanceState;
		post = extras.getParcelable(BUNDLE_POST);
		showCover = extras.getBoolean(BUNDLE_SHOW_COVER);
	}

	private void initValues() {
		PostHtmlUtils htmlUtils = new PostHtmlUtils();

		prepareContentWebView(htmlUtils);

		channelName.setText(post.getChannel().getName());
		channelDescription.setText(post.getChannel().getDescription());
		likeButton.setText(String.valueOf(post.getVotesCount()));
		Glide.with(getContext()).load(post.getChannel().getAvatar()).into(channelAvatar);
	}

	private void prepareContentWebView(PostHtmlUtils htmlUtils) {
		WebSettings webSettings = postContent.getSettings();
		webSettings.setLoadWithOverviewMode(true);
		webSettings.setSupportZoom(true);
		webSettings.setBuiltInZoomControls(false);
		webSettings.setDomStorageEnabled(true);
		webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
		webSettings.setDefaultTextEncodingName(StringConstants.UTF_ENCODING);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			postContent.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		} else {
			postContent.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		}

		String content = htmlUtils.prepareHtmlStyling(getContext(), post.getTitle(), post.getContent());
		postContent.loadDataWithBaseURL(null, content, PostHtmlUtils.Companion.getDEFAULT_MIME_TYPE(),
				PostHtmlUtils.Companion.getDEFAULT_ENCODING(), null);
		postContent.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		postContent.setOnScrollChangedCallback(new ObservableWebView.OnScrollChangedCallback() {

			@Override
			public void onScroll(int scrollY, boolean clampedY) {
				if (scrollY == 0 && finishedScrolling) {
					coverPhotoHolder.expandSheet(true);
					finishedScrolling = false;
				}
				finishedScrolling = clampedY;
			}
		});
	}

	private void initUi() {
		observeHandler = new ObserveHandler(getContext());
		voteHandler = new VoteHandler(getContext());

		boolean channelObserved = UserActionsPreferences.getFollowedChannels(getContext())
				.contains(post.getChannel().getId());
		boolean postLiked = UserActionsPreferences.getVotes(getContext()).contains(post.getId());

		observeHandler.changeButtonViewStyle(observeButton, channelObserved);
		voteHandler.changeButtonViewStyle(likeButton, postLiked);
	}

	private void initSheets(View view) {
		coverPhotoHolder = new PostCoverSheetViewHolder(view, post);
		coverPhotoHolder.initSheet(showCover && post.getCoverPhoto() != null);

		loginSheetHolder = new SignInSheetViewHolder(view, this);
		loginSheetHolder.initBottomSheet();
	}

	@Override
	public PostPresenter createPresenter() {
		return new PostPresenter();
	}

	@OnClick(R.id.button_observe)
	public void onObserveClicked() {
		observeHandler.handleObserve(observeButton, post.getChannel().getId(), this);
	}

	@OnClick({R.id.image_channel_avatar, R.id.text_name_channel, R.id.text_description_channel})
	public void onChannelClicked() {
		ChannelActivity.Companion.startActivity(getContext(), post.getChannel());
	}

	@OnClick(R.id.button_comments_post)
	public void onCommentsClicked() {
		Toast.makeText(getContext(), "Funkcja jeszcze nie gotowa.", Toast.LENGTH_SHORT).show();
	}

	@OnClick(R.id.button_like_post)
	public void onLikePostClicked() {
		voteHandler.handleVote(likeButton, post.getId(), this, feedRefreshListener);
	}

	@OnClick(R.id.button_give_tip)
	public void onGiveTipClicked() {
		Toast.makeText(getContext(), "Funkcja jeszcze nie gotowa.", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onLoginRequired() {
		loginSheetHolder.showBottomSheet(true);
	}

	@Override
	public void onLoginSuccessful() {
		loginSheetHolder.showBottomSheet(false);
	}

	@Override
	public void onPause() {
		super.onPause();
		TimeUtils timeUtils = new TimeUtils();
		RestService.getInstance()
				.getApiService()
				.sendReadingTime(post.getId(), timeUtils.getTimeAsHHMMSS(System.currentTimeMillis() - readingStartTime))
				.enqueue(new Callback<Post>() {
					@Override
					public void onResponse(Call<Post> call, Response<Post> response) {
						// do nothing
					}

					@Override
					public void onFailure(Call<Post> call, Throwable t) {
						// do nothing
					}
				});
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putParcelable(BUNDLE_POST, post);
		outState.putBoolean(BUNDLE_SHOW_COVER, showCover);
		super.onSaveInstanceState(outState);
	}
}