package com.greak.ui.screens.post.clickhandlers;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;
import android.widget.Toast;

import com.greak.R;
import com.greak.data.database.UserInstance;

public class SubscribeHandler {

	public void handleSubscribe(Context context, TextView button, long channelId) {
		boolean subscribed = button.getText().equals(context.getString(R.string.subscribe));

		if (UserInstance.getInstance().isLogged()) {
			button.setBackgroundResource(getButtonBackground(subscribed));
			button.setText(getButtonText(subscribed));
			button.setTextColor(ContextCompat.getColor(context, getButtonTextColor(subscribed)));

			subscribeToChannel(subscribed, channelId);
		} else {
			Toast.makeText(context, R.string.account_required_login, Toast.LENGTH_SHORT).show();
		}
	}

	@DrawableRes
	public int getButtonBackground(boolean subscribed) {
		if (subscribed) {
			return R.drawable.button_rounded_10_teal_filled;
		} else {
			return R.drawable.button_rounded_10_teal;
		}
	}

	@StringRes
	public int getButtonText(boolean subscribed) {
		if (subscribed) {
			return R.string.unsubscribe;
		} else {
			return R.string.subscribe;
		}
	}

	@ColorRes
	public int getButtonTextColor(boolean subscribed) {
		if (subscribed) {
			return android.R.color.white;
		} else {
			return R.color.teal_250;
		}
	}

	public void subscribeToChannel(boolean subscribed, long channelId) {
	}
}