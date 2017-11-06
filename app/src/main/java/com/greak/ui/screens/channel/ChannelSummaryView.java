package com.greak.ui.screens.channel;

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
import com.greak.data.models.Channel;
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

	@BindView(R.id.text_observing_count)
	protected TextView observingCount;

	@BindView(R.id.button_observe)
	protected TextView observeButton;

	@Getter
	private View view;
	private Channel channel;
	private ObserveHandler observeHandler;
	private OnLoginRequired onLoginRequired;

	public ChannelSummaryView(@NonNull Context context, ViewGroup container, @NonNull Channel channel,
	                          OnLoginRequired onLoginRequired) {
		view = LayoutInflater.from(context).inflate(R.layout.item_channel_summary, container, false);
		ButterKnife.bind(this, view);

		initObserveButton(context, channel);
		initValues(context, channel);

		this.channel = channel;
		this.onLoginRequired = onLoginRequired;
	}

	private void initObserveButton(@NonNull Context context, @NonNull Channel channel) {
		boolean channelObserved = UserActionsPreferences.getFollowedChannels(context).contains(channel.getId());
		observeHandler = new ObserveHandler(context);
		observeHandler.changeButtonViewStyle(observeButton, channelObserved);
	}

	private void initValues(Context context, @NonNull Channel channel) {
		Glide.with(context).load(channel.getAvatar()).into(avatar);
		title.setText(channel.getName());
		description.setText(channel.getDescription());
		postCount.setText(String.valueOf(channel.getPostCount()));
		observingCount.setText(String.valueOf(channel.getSubscriptionCount()));
	}

	@OnClick(R.id.button_observe)
	public void onObserveClicked() {
		observeHandler.handleObserve(observeButton, channel.getId(), onLoginRequired);
	}
}