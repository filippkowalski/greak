package com.greak.ui.screens.main.feed;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.chrono.src.ui.list.OnItemClickListener;
import com.chrono.src.ui.list.endless.EndlessListFragment;
import com.chrono.src.ui.states.error.ErrorView;
import com.chrono.src.ui.states.error.StandardErrorView;
import com.greak.R;
import com.greak.data.database.UserInstance;
import com.greak.data.models.FeedItem;
import com.greak.data.models.Post;
import com.greak.ui.common.FragmentCommunicationUtils;
import com.greak.ui.common.resolvers.OnLoginListener;
import com.greak.ui.screens.main.MainActivity;
import com.greak.ui.screens.main.common.FeedVoteViewHandler;
import com.greak.ui.screens.main.common.OnRefreshListener;
import com.greak.ui.screens.main.common.PostSpacingItemDecoration;
import com.greak.ui.screens.main.common.PostsMultiAdapter;
import com.greak.ui.screens.main.common.ScrollableToTop;
import com.greak.ui.screens.main.feed.emptystate.LoginEmptyView;
import com.greak.ui.screens.post.PostActivity;

import java.util.List;

public class FeedListFragment extends EndlessListFragment<Post, FeedItem, PostsMultiAdapter> implements
		OnItemClickListener<Post>, OnLoginListener, ScrollableToTop, FeedView {

	private OnRefreshListener refreshListener;
	private FeedPresenter presenter;

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		refreshListener = FragmentCommunicationUtils.getListenerOrThrow(this, OnRefreshListener.class);
	}
	public static FeedListFragment newInstance() {
		return new FeedListFragment();
	}

	@Override
	public FeedPresenter createPresenter() {
		presenter = new FeedPresenter(this);
		presenter.setView(this);
		return presenter;
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
		PostActivity.startActivityForResult(FeedListFragment.this, post, false, position);
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
					getString(R.string.no_followed_users_yet), getString(R.string.follow_something), getString(R.string.refresh));
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