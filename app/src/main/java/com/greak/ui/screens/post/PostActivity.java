package com.greak.ui.screens.post;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.greak.R;
import com.greak.data.database.UserInstance;
import com.greak.data.models.Post;
import com.greak.ui.base.BaseActivity;
import com.greak.ui.screens.main.common.FeedVoteViewHandler;
import com.greak.ui.screens.main.filtered_lists.OnFeedRefreshListener;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;

/**
 * Created by Filip Kowalski on 26.03.17.
 */

public class PostActivity extends BaseActivity implements OnFeedRefreshListener {

	public static final int FEED_REFRESH_RESULT = 3006;

	private static final String EXTRA_POST = "post";
	private static final String EXTRA_SHOW_COVER = "show_cover";
	public static final String EXTRA_FROM_NOTIFICATION_ID = "comes_from_notification";

	private Post post;
	private boolean showCover;
	private int postPosition;

	public static void startActivityForResult(Fragment fragment, Post post, boolean showCover, int postPosition) {
		Intent intent = createIntent(fragment.getContext(), post, showCover, postPosition);
		fragment.startActivityForResult(intent, FEED_REFRESH_RESULT);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		recreate();
	}

	@NonNull
	public static Intent createIntent(Context context, Post post, boolean showCover, int postPosition) {
		Intent intent = new Intent(context, PostActivity.class);
		intent.putExtra(EXTRA_POST, post);
		intent.putExtra(EXTRA_SHOW_COVER, showCover);
		intent.putExtra(FeedVoteViewHandler.EXTRA_POST_POSITION, postPosition);
		return intent;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_fragment_container_without_toolbar);
		ButterKnife.bind(this);

		initArguments(savedInstanceState);
		initPostFragment();
	}

	private void initArguments(Bundle savedInstanceState) {
		Bundle extras = savedInstanceState == null ? getIntent().getExtras() : savedInstanceState;
		post = extras.getParcelable(EXTRA_POST);
		showCover = extras.getBoolean(EXTRA_SHOW_COVER);
		postPosition = extras.getInt(FeedVoteViewHandler.EXTRA_POST_POSITION);

		int notificationId = extras.getInt(EXTRA_FROM_NOTIFICATION_ID, 0);
		logIfIntentComesFromNotification(notificationId);
	}

	private void logIfIntentComesFromNotification(int notificationId) {
		if (notificationId != 0 && UserInstance.getInstance().isLogged()) {
			Map<String, Boolean> map = new HashMap<>();
			map.put("is_read", true);
//			RestService.getInstance().getApiService().setNotificationAsRead(notificationId,
//					UserInstance.getInstance().getAccount().getUsername(), map).enqueue(new Callback<Void>() {
//				@Override
//				public void onResponse(Call<Void> call, Response<Void> response) {
//					// do nothing
//				}
//
//				@Override
//				public void onFailure(Call<Void> call, Throwable t) {
//					// do nothing
//				}
//			});
		}
	}

	private void initPostFragment() {
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.container_fragment, PostFragment.newInstance(post, showCover))
				.commit();
	}

	@Override
	public void refreshFeed(int votesCount) {
		Intent data = new Intent();
		data.putExtra(FeedVoteViewHandler.EXTRA_POST_POSITION, postPosition);
		data.putExtra(FeedVoteViewHandler.EXTRA_VOTES_COUNT, votesCount);
		setResult(RESULT_OK, data);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putParcelable(EXTRA_POST, post);
		outState.putBoolean(EXTRA_SHOW_COVER, showCover);
		outState.putInt(FeedVoteViewHandler.EXTRA_POST_POSITION, postPosition);
		super.onSaveInstanceState(outState);
	}
}