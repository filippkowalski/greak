package com.greak.ui.screens.main.trending;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.chrono.src.ui.list.OnItemClickListener;
import com.chrono.src.ui.list.adapters.decoration.DefaultSpacingItemDecoration;
import com.chrono.src.ui.list.endless.EndlessListFragment;
import com.greak.data.models.FeedItem;
import com.greak.data.models.Post;
import com.greak.ui.screens.main.common.FeedVoteViewHandler;
import com.greak.ui.screens.main.common.PostsMultiAdapter;
import com.greak.ui.screens.main.common.ScrollableToTop;
import com.greak.ui.screens.post.PostActivity;

import java.util.List;

public class TrendingListFragment extends EndlessListFragment<Post, FeedItem, PostsMultiAdapter>
		implements ScrollableToTop, OnItemClickListener<Post> {

	public static TrendingListFragment newInstance() {
		return new TrendingListFragment();
	}

	@Override
	public TrendingListPresenter createPresenter() {
		return new TrendingListPresenter(this);
	}

	@Override
	public PostsMultiAdapter createAdapter(List<FeedItem> data) {
		return new PostsMultiAdapter(getContext(), data, this);
	}

	@Override
	protected void addItemsToAdapter(List<Post> data, boolean firstRun) {
		getAdapter().addItems(data, firstRun);
	}

	@Override
	protected RecyclerView.ItemDecoration getItemDecorator() {
		return new DefaultSpacingItemDecoration(0);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK && requestCode == PostActivity.FEED_REFRESH_RESULT) {
			FeedVoteViewHandler viewHandler = new FeedVoteViewHandler();
			viewHandler.handleVoteRefresh(getAdapter(), data);
		}
	}

	@Override
	public void scrollToTop() {
		getRecyclerView().smoothScrollToPosition(0);
	}

	@Override
	public void onItemClick(Post post, int position) {
		PostActivity.startActivityForResult(TrendingListFragment.this, post, false, position);
	}
}