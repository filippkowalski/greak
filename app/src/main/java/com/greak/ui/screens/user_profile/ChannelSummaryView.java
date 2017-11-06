package com.greak.ui.screens.user_profile;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.greak.R;
import com.greak.data.database.UserActionsPreferences;
import com.greak.data.models.SteemAccount;
import com.greak.ui.screens.post.clickhandlers.OnLoginRequired;
import com.greak.ui.screens.post.clickhandlers.ObserveHandler;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lombok.Getter;

public class ChannelSummaryView {

	@BindView(R.id.image_channel_avatar)
	protected ImageView avatar;

	@BindView(R.id.text_name_channel)
	protected TextView title;

	@BindView(R.id.text_description_channel)
	protected TextView description;

	@BindView(R.id.text_post_count)
	protected TextView postCount;

	@BindView(R.id.text_followers_count)
	protected TextView followersCount;

	@BindView(R.id.button_observe)
	protected TextView observeButton;

	@Getter
	private View view;
	private SteemAccount steemAccount;
	private ObserveHandler observeHandler;
	private OnLoginRequired onLoginRequired;

	public ChannelSummaryView(@NonNull Context context, ViewGroup container, @NonNull SteemAccount steemAccount,
	                          OnLoginRequired onLoginRequired) {
		view = LayoutInflater.from(context).inflate(R.layout.item_channel_summary, container, false);
		ButterKnife.bind(this, view);

		initObserveButton(context, steemAccount);
		initValues(context, steemAccount);

		this.steemAccount = steemAccount;
		this.onLoginRequired = onLoginRequired;
	}

	private void initObserveButton(@NonNull Context context, @NonNull SteemAccount steemAccount) {
		boolean channelObserved = UserActionsPreferences.getFollowedChannels(context).contains(steemAccount.getId());
		observeHandler = new ObserveHandler(context);
		observeHandler.changeButtonViewStyle(observeButton, channelObserved);
	}

	private void initValues(Context context, @NonNull SteemAccount steemAccount) {
		Glide.with(context).load(steemAccount.getAvatar()).into(avatar);
		title.setText(steemAccount.getName());
		description.setText(steemAccount.getDescription());
		postCount.setText(String.valueOf(steemAccount.getPostCount()));
		followersCount.setText(String.valueOf(steemAccount.getFollowersCount()));
	}

	@OnClick(R.id.button_observe)
	public void onObserveClicked() {
		observeHandler.handleObserve(observeButton, steemAccount.getId(), onLoginRequired);
	}
}