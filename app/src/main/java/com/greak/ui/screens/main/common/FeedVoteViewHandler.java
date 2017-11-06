package com.greak.ui.screens.main.common;

import android.content.Intent;

import com.chrono.src.ui.list.adapters.multitype.MultiTypeRecyclerAdapter;
import com.greak.data.models.FeedItem;
import com.greak.data.models.Post;

public class FeedVoteViewHandler {

	public static final String EXTRA_POST_POSITION = "post_position";
	public static final String EXTRA_VOTES_COUNT = "votes_count";

	public void handleVoteRefresh(MultiTypeRecyclerAdapter<FeedItem> adapter, Intent data) {
		int postPosition = data.getIntExtra(EXTRA_POST_POSITION, 0);
		int votesCount = data.getIntExtra(EXTRA_VOTES_COUNT, 0);
		if (adapter.getItem(postPosition) instanceof Post) {
			((Post) adapter.getItem(postPosition)).setVotesCount(votesCount);
			adapter.notifyItemChanged(postPosition);
		}
	}
}
