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
import com.greak.data.models.Vote;
import com.greak.data.rest.ApiService;
import com.greak.data.rest.RestService;
import com.greak.ui.screens.main.discover.OnFeedRefreshListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
			return R.drawable.ic_thumb_up_white_16dp;
		} else {
			return R.drawable.ic_thumb_up_grey_16dp;
		}
	}

	private void votePost(boolean liked, final long postId, final TextView button) {
		ApiService service = RestService.getInstance().getApiService();
		if (liked) {
			service.unvotePost(postId).enqueue(new Callback<Vote>() {
				@Override
				public void onResponse(Call<Vote> call, Response<Vote> response) {
					if (response.isSuccessful()) {
						UserActionsPreferences.removeVote(context, postId);
						changeButtonViewStyle(button, false);

						int votesCount = response.body().getCurrentVotesCount();
						setButtonValue(votesCount, button);
						feedRefreshListener.refreshFeed(votesCount);
					}
				}

				@Override
				public void onFailure(Call<Vote> call, Throwable t) {
					showFailureToast();
				}
			});
		} else {
			service.votePost(postId).enqueue(new Callback<Vote>() {
				@Override
				public void onResponse(Call<Vote> call, Response<Vote> response) {
					if (response.isSuccessful()) {
						List<Long> postIds = new ArrayList<>();
						postIds.add(postId);

						UserActionsPreferences.setVotes(context, postIds);
						changeButtonViewStyle(button, true);

						int votesCount = response.body().getCurrentVotesCount();
						setButtonValue(votesCount, button);
						feedRefreshListener.refreshFeed(votesCount);
					}
				}

				@Override
				public void onFailure(Call<Vote> call, Throwable t) {
					Toast.makeText(context, R.string.error_operation_failed, Toast.LENGTH_SHORT).show();
				}
			});
		}
	}

	private void showFailureToast() {
		Toast.makeText(context, R.string.error_operation_failed, Toast.LENGTH_SHORT).show();
	}
}