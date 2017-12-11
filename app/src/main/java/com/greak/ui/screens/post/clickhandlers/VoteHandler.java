package com.greak.ui.screens.post.clickhandlers;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;
import android.widget.Toast;

import com.greak.R;
import com.greak.data.converters.VoteService;
import com.greak.data.database.UserActionsPreferences;
import com.greak.data.database.UserInstance;
import com.greak.data.models.Account;
import com.greak.ui.screens.main.filtered_lists.OnFeedRefreshListener;

import java.util.ArrayList;
import java.util.List;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class VoteHandler {

	private Context context;
	private OnFeedRefreshListener feedRefreshListener;

	public VoteHandler(Context context) {
		this.context = context;
	}

	public void handleVote(TextView button, String permlink, OnLoginRequired listener, OnFeedRefreshListener
			feedRefreshListener) {
		// TODO implement voting
//		boolean liked = UserActionsPreferences.getVotes(context).contains(permlink);
//		this.feedRefreshListener = feedRefreshListener;
//
//		if (UserInstance.getInstance().isLogged()) {
//			vote(button, permlink, liked);
//		} else {
//			listener.onLoginRequired();
//		}
	}

	private void vote(TextView button, String permlink, boolean liked) {
		try {
			votePost(liked, permlink, button);
		} catch (SteemResponseException | SteemCommunicationException e) {
			e.printStackTrace();
			showFailureToast();
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

	private void votePost(boolean liked, final String permlink, final TextView button)
			throws SteemResponseException, SteemCommunicationException {
		Account account = UserInstance.getInstance().getAccount();
		AccountName accountName = new AccountName(account.getUsername());
		VoteService voteService = new VoteService(accountName, permlink);
		getVoteObservable(liked, voteService).subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.doOnNext(voted -> handleVoted(voted, permlink, button))
				.onErrorReturn(throwable -> {
					throwable.printStackTrace();
					showFailureToast();
					return false;
				})
				.subscribe();
	}

	private Observable<Boolean> getVoteObservable(boolean liked, VoteService voteService)
			throws SteemResponseException, SteemCommunicationException {
		if (liked) {
			return voteService.unvotePost();
		} else {
			return voteService.votePost();
		}
	}

	private void handleVoted(Boolean voted, String permlink, TextView button) {
		if (voted) {
			List<String> permLinks = new ArrayList<>();
			permLinks.add(permlink);

			UserActionsPreferences.setVotes(context, permLinks);
			changeButtonViewStyle(button, true);
		} else {
			UserActionsPreferences.removeVote(context, permlink);
			changeButtonViewStyle(button, false);
		}
	}

	private void showFailureToast() {
		Toast.makeText(context, R.string.error_operation_failed, Toast.LENGTH_SHORT).show();
	}
}