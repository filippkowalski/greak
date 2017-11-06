package com.greak.ui.screens.post.clickhandlers;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.greak.R;
import com.greak.data.database.UserActionsPreferences;
import com.greak.data.database.UserInstance;

public class ObserveHandler {

	private Context context;

	public ObserveHandler(Context context) {
		this.context = context;
	}

	public void handleObserve(TextView button, long channelId, OnLoginRequired listener) {
		boolean subscribed = UserActionsPreferences.getFollowedChannels(context).contains(channelId);

		if (UserInstance.getInstance().isLogged()) {
			observeChannel(subscribed, channelId, button);
		} else {
			listener.onLoginRequired();
		}
	}

	public void changeButtonViewStyle(TextView button, boolean followed) {
		button.setBackgroundResource(getButtonBackground(followed));
		button.setText(getButtonText(followed));
		button.setTextColor(ContextCompat.getColor(context, getButtonTextColor(followed)));
	}

	@DrawableRes
	private int getButtonBackground(boolean followed) {
		if (followed) {
			return R.drawable.button_rounded_100_teal_filled;
		} else {
			return R.drawable.button_rounded_100_teal;
		}
	}

	@StringRes
	private int getButtonText(boolean followed) {
		if (followed) {
			return R.string.unfollow;
		} else {
			return R.string.follow;
		}
	}

	@ColorRes
	private int getButtonTextColor(boolean followed) {
		if (followed) {
			return android.R.color.white;
		} else {
			return R.color.teal_250;
		}
	}

	private void observeChannel(final boolean followed, final long channelId, final TextView button) {
	}
}