package com.greak.ui.screens.post.clickhandlers;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;
import android.widget.Toast;

import com.greak.R;
import com.greak.data.database.UserActionsPreferences;
import com.greak.data.database.UserInstance;
import com.greak.ui.screens.main.filtered_lists.OnFeedRefreshListener;

public class VoteHandler {

	private Context context;
	private OnFeedRefreshListener feedRefreshListener;

	public VoteHandler(Context context) {
		this.context = context;
	}

	public void handleVote(TextView button, long postId, OnLoginRequired listener, OnFeedRefreshListener feedRefreshListener) {
		boolean liked = UserActionsPreferences.getVotes(context).contains(postId);
		this.feedRefreshListener = feedRefreshListener;

		if (UserInstance.getInstance().isLogged()) {
			votePost(liked, postId, button);
		} else {
			listener.onLoginRequired();
		}
	}

	public void changeButtonViewStyle(TextView button, boolean postLiked) {
		button.setTextColor(ContextCompat.getColor(context, getButtonTextColor(postLiked)));
		button.setBackgroundResource(getButtonBackground(postLiked));
		button.setCompoundDrawablesWithIntrinsicBounds(0, 0, getButtonDrawable(postLiked), 0);
	}

	@DrawableRes
	private int getButtonBackground(boolean liked) {
		if (liked) {
			return R.drawable.button_purple_gradient;
		} else {
			return R.drawable.button_grey;
		}
	}

	@ColorRes
	private int getButtonTextColor(boolean liked) {
		if (liked) {
			return android.R.color.white;
		} else {
			return R.color.grey_666666;
		}
	}

	private void setButtonValue(int voteCount, TextView button) {
		button.setText(String.valueOf(voteCount));
	}

	@DrawableRes
	private int getButtonDrawable(boolean liked) {
		if (liked) {
			return R.drawable.ic_money_grey_small;
		} else {
			return R.drawable.ic_money_grey_small;
		}
	}

	private void votePost(boolean liked, final long postId, final TextView button) {

	}

	private void showFailureToast() {
		Toast.makeText(context, R.string.error_operation_failed, Toast.LENGTH_SHORT).show();
	}
}