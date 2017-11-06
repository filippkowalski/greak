package com.greak.ui.screens.main.votes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.chrono.src.ui.list.OnItemClickListener;
import com.chrono.src.ui.list.endless.EndlessListFragment;
import com.chrono.src.ui.states.error.ErrorView;
import com.chrono.src.ui.states.error.StandardErrorView;
import com.greak.R;
import com.greak.data.database.UserInstance;
import com.greak.data.database.UserManager;
import com.greak.data.models.Account;
import com.greak.data.models.FeedItem;
import com.greak.data.models.Post;
import com.greak.ui.common.FragmentCommunicationUtils;
import com.greak.ui.common.resolvers.OnLoginListener;
import com.greak.ui.screens.main.common.FeedVoteViewHandler;
import com.greak.ui.screens.main.common.OnRefreshListener;
import com.greak.ui.screens.main.common.PostSpacingItemDecoration;
import com.greak.ui.screens.main.common.PostsMultiAdapter;
import com.greak.ui.screens.main.common.ScrollableToTop;
import com.greak.ui.screens.main.feed.emptystate.LoginEmptyView;
import com.greak.ui.screens.post.PostActivity;

import java.util.List;

public class VotesListFragment extends EndlessListFragment<Post, FeedItem, PostsMultiAdapter> implements
		OnItemClickListener<Post>, OnLoginListener, ScrollableToTop {

	private OnRefreshListener refreshListener;

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		refreshListener = FragmentCommunicationUtils.getListenerOrThrow(this, OnRefreshListener.class);
	}

	public static VotesListFragment newInstance() {
		return new VotesListFragment();
	}

	@Override
	public VotesPresenter createPresenter() {
		return new VotesPresenter(this);
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
	public void onItemClick(Post post, int position) {
		PostActivity.startActivityForResult(VotesListFragment.this, post, false, position);
	}

	@Override
	protected RecyclerView.ItemDecoration getItemDecorator() {
		int topListSpacing = getResources().getDimensionPixelSize(R.dimen.top_list_spacing);
		int sidesListSpacing = getResources().getDimensionPixelSize(R.dimen.side_list_spacing);
		return new PostSpacingItemDecoration(topListSpacing, sidesListSpacing);
	}

	@Override
	protected ErrorView createErrorView() {
		if (UserInstance.getInstance().isLogged()) {
			return new StandardErrorView(getContext(), v -> onRefresh(),
					getString(R.string.no_favorites_yet), getString(R.string.like_something),
					getString(R.string.refresh));
		} else {
			return new LoginEmptyView(this);
		}
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

	@Override
	public void onUserLogin(String username) {
		refreshListener.onRefresh();
		UserManager userManager = new UserManager(getContext());
		userManager.setAccount(new Account(username));
	}
}