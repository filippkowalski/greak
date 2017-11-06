package com.greak.ui.screens.main.discover;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.chrono.src.ui.list.OnItemClickListener;
import com.chrono.src.ui.list.adapters.decoration.DefaultSpacingItemDecoration;
import com.chrono.src.ui.list.endless.EndlessListFragment;
import com.greak.data.models.Category;
import com.greak.data.models.Channel;
import com.greak.data.models.DiscoverItem;
import com.greak.data.models.FeedItem;
import com.greak.data.models.Post;
import com.greak.ui.screens.category.CategoryActivity;
import com.greak.ui.screens.channel.ChannelActivity;
import com.greak.ui.screens.main.common.FeedVoteViewHandler;
import com.greak.ui.screens.main.common.ScrollableToTop;
import com.greak.ui.screens.post.PostActivity;

import java.util.ArrayList;
import java.util.List;

public class DiscoverListFragment extends EndlessListFragment<DiscoverItem, DiscoverItem, DiscoverMultiAdapter>
		implements ScrollableToTop {

	public static DiscoverListFragment newInstance() {
		return new DiscoverListFragment();
	}

	@Override
	public DiscoverPresenter createPresenter() {
		return new DiscoverPresenter(this);
	}

	@Override
	public DiscoverMultiAdapter createAdapter(List<DiscoverItem> data) {
		return new DiscoverMultiAdapter(getContext(), convertDiscoverItems(data), getPostClickListener(),
				getChannelClickListener(), getCategoryClickListener());
	}

	@Override
	protected void addItemsToAdapter(List<DiscoverItem> elements, boolean firstRun) {
		getAdapter().addItems(convertDiscoverItems(elements), firstRun);
	}

	public List<FeedItem> convertDiscoverItems(List<DiscoverItem> elements) {
		List<FeedItem> feedItems = new ArrayList<>();
		for (DiscoverItem discoverItem : elements) {
			feedItems.add((FeedItem) discoverItem.getObject());
		}
		return feedItems;
	}

	private OnItemClickListener<Post> getPostClickListener() {
		return new OnItemClickListener<Post>() {
			@Override
			public void onItemClick(Post post, int position) {
				PostActivity.startActivityForResult(DiscoverListFragment.this, post, true, position);
			}
		};
	}

	private OnItemClickListener<Channel> getChannelClickListener() {
		return new OnItemClickListener<Channel>() {
			@Override
			public void onItemClick(Channel channel, int position) {
				ChannelActivity.Companion.startActivity(getContext(), channel);
			}
		};
	}

	private OnItemClickListener<Category> getCategoryClickListener() {
		return new OnItemClickListener<Category>() {
			@Override
			public void onItemClick(Category category, int position) {
				CategoryActivity.startActivity(getContext(), category);
			}
		};
	}

	@Override
	protected RecyclerView.ItemDecoration getItemDecorator() {
		return new DefaultSpacingItemDecoration(0);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK && requestCode == PostActivity.FEED_REFRESH_RESULT ) {
			FeedVoteViewHandler viewHandler = new FeedVoteViewHandler();
			viewHandler.handleVoteRefresh(getAdapter(), data);
		}
	}

	@Override
	public void scrollToTop() {
		getRecyclerView().smoothScrollToPosition(0);
	}
}