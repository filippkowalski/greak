package com.greak.ui.screens.post.clickhandlers;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;
import android.widget.Toast;

import com.greak.R;
import com.greak.data.database.UserActionsPreferences;
import com.greak.data.database.UserInstance;
import com.greak.data.models.Follow;
import com.greak.data.rest.ApiService;
import com.greak.data.rest.RestService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

	public void changeButtonViewStyle(TextView button, boolean subscribed) {
		button.setBackgroundResource(getButtonBackground(subscribed));
		button.setText(getButtonText(subscribed));
		button.setTextColor(ContextCompat.getColor(context, getButtonTextColor(subscribed)));
	}

	@DrawableRes
	private int getButtonBackground(boolean subscribed) {
		if (subscribed) {
			return R.drawable.button_rounded_100_teal_filled;
		} else {
			return R.drawable.button_rounded_100_teal;
		}
	}

	@StringRes
	private int getButtonText(boolean subscribed) {
		if (subscribed) {
			return R.string.observing;
		} else {
			return R.string.observe;
		}
	}

	@ColorRes
	private int getButtonTextColor(boolean subscribed) {
		if (subscribed) {
			return android.R.color.white;
		} else {
			return R.color.teal_250;
		}
	}

	private void observeChannel(final boolean observed, final long channelId, final TextView button) {
		ApiService service = RestService.getInstance().getApiService();
		if (observed) {
			service.unfollowChannel(channelId).enqueue(new Callback<Follow>() {
				@Override
				public void onResponse(Call<Follow> call, Response<Follow> response) {
					UserActionsPreferences.removeFollowedChannel(context, channelId);
					changeButtonViewStyle(button, false);
				}

				@Override
				public void onFailure(Call<Follow> call, Throwable t) {
					showFailureToast();
				}
			});
		} else {
			service.followChannel(channelId).enqueue(new Callback<Follow>() {
				@Override
				public void onResponse(Call<Follow> call, Response<Follow> response) {
					List<Long> channelIds = new ArrayList<>();
					channelIds.add(channelId);
					UserActionsPreferences.setFollowedChannels(context, channelIds);
					changeButtonViewStyle(button, true);
				}

				@Override
				public void onFailure(Call<Follow> call, Throwable t) {
					showFailureToast();
				}
			});
		}
	}

	private void showFailureToast() {
		Toast.makeText(context, R.string.error_operation_failed, Toast.LENGTH_SHORT).show();
	}
}