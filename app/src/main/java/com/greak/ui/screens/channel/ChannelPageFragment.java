package com.greak.ui.screens.channel;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chrono.src.ui.list.OnItemClickListener;
import com.chrono.src.ui.list.adapters.headerFooterAdapter.RecyclerViewHeaderFooterAdapter;
import com.chrono.src.ui.list.endless.EndlessListFragment;
import com.greak.R;
import com.greak.common.utils.StatsConstants;
import com.greak.data.models.Channel;
import com.greak.data.models.Post;
import com.greak.ui.analytics.FabricAnalyticsManager;
import com.greak.ui.common.resolvers.OnLoginListener;
import com.greak.ui.screens.main.common.FeedVoteViewHandler;
import com.greak.ui.screens.post.PostActivity;
import com.greak.ui.screens.post.SignInSheetViewHolder;
import com.greak.ui.screens.post.clickhandlers.OnLoginRequired;

import java.util.List;

public class ChannelPageFragment extends EndlessListFragment<Post, Post,
		RecyclerViewHeaderFooterAdapter<Post, ChannelSummaryMultiAdapter>> implements OnItemClickListener<Post>,
		OnLoginRequired, OnLoginListener {

	private static final String BUNDLE_CHANNEL = "channel";
	private Channel channel;

	private SignInSheetViewHolder loginSheetHolder;

	public static ChannelPageFragment newInstance(Channel channel) {
		Bundle args = new Bundle();
		args.putParcelable(BUNDLE_CHANNEL, channel);
		ChannelPageFragment fragment = new ChannelPageFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);
		initLoginBottomSheet();

		FabricAnalyticsManager.logEvent(StatsConstants.CHANNEL, channel.getName());

		return view;
	}

	@Override
	protected void initArguments() {
		channel = getArguments().getParcelable(BUNDLE_CHANNEL);
	}

	@Override
	protected int getLayout() {
		return R.layout.fragment_channel_page;
	}

	private void initLoginBottomSheet() {
		loginSheetHolder = new SignInSheetViewHolder(getView(), this);
		loginSheetHolder.initBottomSheet();
	}

	@Override
	public ChannelPagePresenter createPresenter() {
		return new ChannelPagePresenter(channel.getId(), this);
	}

	@Override
	public RecyclerViewHeaderFooterAdapter<Post, ChannelSummaryMultiAdapter> createAdapter(List<Post> data) {
		ChannelSummaryMultiAdapter internalAdapter = new ChannelSummaryMultiAdapter(getContext(), data, this);
		RecyclerViewHeaderFooterAdapter<Post, ChannelSummaryMultiAdapter> adapter =
				new RecyclerViewHeaderFooterAdapter<>(getRecyclerView().getLayoutManager(), internalAdapter);
		adapter.addHeader(createChannelSummary());
		return adapter;
	}

	@Override
	protected void addItemsToAdapter(List<Post> data, boolean firstRun) {
		getAdapter().addItems(data, firstRun);
	}

	private View createChannelSummary() {
		ChannelSummaryView channelSummaryView = new ChannelSummaryView(getContext(), getRecyclerView(), channel, this);
		return channelSummaryView.getView();
	}

	@Override
	public void onItemClick(Post post, int position) {
		PostActivity.startActivityForResult(ChannelPageFragment.this, post, true, position);
	}

	@Override
	protected RecyclerView.ItemDecoration getItemDecorator() {
		int topListSpacing = getResources().getDimensionPixelSize(R.dimen.top_list_spacing);
		int sidesListSpacing = getResources().getDimensionPixelSize(R.dimen.side_list_spacing);
		return new ChannelSpacingItemDecoration(topListSpacing, sidesListSpacing);
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
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK && requestCode == PostActivity.FEED_REFRESH_RESULT) {
			int postPosition = data.getIntExtra(FeedVoteViewHandler.EXTRA_POST_POSITION, 0);
			int votesCount = data.getIntExtra(FeedVoteViewHandler.EXTRA_VOTES_COUNT, 0);

			ChannelSummaryMultiAdapter internalAdapter = getAdapter().getInternalAdapter();
			internalAdapter.getItem(postPosition).setVotesCount(votesCount);
			internalAdapter.notifyItemChanged(postPosition);
		}
	}
}