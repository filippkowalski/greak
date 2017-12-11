package com.greak.ui.screens.user_profile;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chrono.src.ui.list.OnItemClickListener;
import com.chrono.src.ui.list.adapters.headerFooterAdapter.RecyclerViewHeaderFooterAdapter;
import com.chrono.src.ui.list.endless.EndlessListFragment;
import com.greak.R;
import com.greak.common.utils.StatsConstants;
import com.greak.data.models.Post;
import com.greak.data.models.SteemAccount;
import com.greak.ui.analytics.FabricAnalyticsManager;
import com.greak.ui.common.resolvers.OnLoginListener;
import com.greak.ui.screens.login.SignInDialogFragment;
import com.greak.ui.screens.main.MainActivity;
import com.greak.ui.screens.main.common.FeedVoteViewHandler;
import com.greak.ui.screens.post.PostActivity;
import com.greak.ui.screens.post.clickhandlers.OnLoginRequired;

import java.util.List;

import eu.bittrade.libs.steemj.base.models.AccountName;

public class UserProfilePageFragment extends EndlessListFragment<Post, Post,
		RecyclerViewHeaderFooterAdapter<Post, UserProfileSummaryMultiAdapter>> implements
		OnItemClickListener<Post>, OnLoginRequired, OnLoginListener, UserProfileView {

	private static final String BUNDLE_USER_PROFILE = "user_profile";
	private SteemAccount steemAccount;
	private UserProfilePresenter presenter;

	public static UserProfilePageFragment newInstance(SteemAccount steemAccount) {
		Bundle args = new Bundle();
		args.putParcelable(BUNDLE_USER_PROFILE, steemAccount);
		UserProfilePageFragment fragment = new UserProfilePageFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);

		FabricAnalyticsManager.logEvent(StatsConstants.CHANNEL, steemAccount.getName());

		return view;
	}

	@Override
	protected void initArguments() {
		steemAccount = getArguments().getParcelable(BUNDLE_USER_PROFILE);
	}

	@Override
	protected int getLayout() {
		return R.layout.fragment_user_profile;
	}

	@Override
	public UserProfilePresenter createPresenter() {
		presenter = new UserProfilePresenter(new AccountName(steemAccount.getName()), this);
		presenter.setView(this);
		return presenter;
	}

	@Override
	public RecyclerViewHeaderFooterAdapter<Post, UserProfileSummaryMultiAdapter> createAdapter(List<Post> data) {
		UserProfileSummaryMultiAdapter internalAdapter = new UserProfileSummaryMultiAdapter(getContext(), data, this);
		RecyclerViewHeaderFooterAdapter<Post, UserProfileSummaryMultiAdapter> adapter =
				new RecyclerViewHeaderFooterAdapter<>(getRecyclerView().getLayoutManager(), internalAdapter);
		adapter.addHeader(createChannelSummary());
		return adapter;
	}

	@Override
	protected void addItemsToAdapter(List<Post> data, boolean firstRun) {
		getAdapter().addItems(data, firstRun);
	}

	private View createChannelSummary() {
		ChannelSummaryView channelSummaryView = new ChannelSummaryView(getContext(), getRecyclerView(), steemAccount, this);
		return channelSummaryView.getView();
	}

	@Override
	public void onItemClick(Post post, int position) {
		PostActivity.startActivityForResult(UserProfilePageFragment.this, post, true, position);
	}

	@Override
	protected RecyclerView.ItemDecoration getItemDecorator() {
		int topListSpacing = getResources().getDimensionPixelSize(R.dimen.top_list_spacing);
		int sidesListSpacing = getResources().getDimensionPixelSize(R.dimen.side_list_spacing);
		return new ChannelSpacingItemDecoration(topListSpacing, sidesListSpacing);
	}

	@Override
	public void onLoginRequired() {
		SignInDialogFragment.newInstance().show(getChildFragmentManager(), null);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK && requestCode == PostActivity.FEED_REFRESH_RESULT) {
			int postPosition = data.getIntExtra(FeedVoteViewHandler.EXTRA_POST_POSITION, 0);
			int votesCount = data.getIntExtra(FeedVoteViewHandler.EXTRA_VOTES_COUNT, 0);

			UserProfileSummaryMultiAdapter internalAdapter = getAdapter().getInternalAdapter();
			internalAdapter.getItem(postPosition).setVotesCount(votesCount);
			internalAdapter.notifyItemChanged(postPosition);
		}
	}

	@Override
	public void onUserLogin(String username, String password) {
		presenter.onUserLogin(getContext(), username, password);
	}

	@Override
	public void onUserLoggedIn(boolean loggedInSuccessfully) {
		if (loggedInSuccessfully) {
			Toast.makeText(getContext(), R.string.logged_in_successfully, Toast.LENGTH_SHORT).show();
			restartActivity();
		} else {
			Toast.makeText(getContext(), R.string.failed_to_login, Toast.LENGTH_SHORT).show();
		}
	}

	private void restartActivity() {
		getActivity().finish();
		MainActivity.startActivity(getContext());
	}
}