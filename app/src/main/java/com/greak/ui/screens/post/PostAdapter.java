package com.greak.ui.screens.post;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chrono.src.ui.list.OnItemClickListener;
import com.chrono.src.ui.list.adapters.multitype.MultiTypeAdapter;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.greak.R;
import com.greak.data.database.UserActionsPreferences;
import com.greak.data.models.Post;
import com.greak.ui.common.TagViewUtils;
import com.greak.ui.common.TimeUtils;
import com.greak.ui.screens.channel.ChannelActivity;
import com.greak.ui.screens.post.clickhandlers.VoteHandler;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Filip Kowalski on 15.03.17.
 */

public class PostAdapter implements MultiTypeAdapter<PostAdapter.ViewHolder, Post> {

	private Context context;
	private OnItemClickListener<Post> listener;

	public PostAdapter(Context context, OnItemClickListener<Post> listener) {
		this.context = context;
		this.listener = listener;
	}

	@Override
	public ViewHolder getViewHolder(ViewGroup viewGroup) {
		View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_post, viewGroup, false);
		return new ViewHolder(view);
	}

	@Override
	public void populateViewHolder(ViewHolder holder, final int position, final Post post) {
		Glide.with(context).load(post.getChannel().getAvatar()).into(holder.avatar);
		Glide.with(context).load(post.getCoverPhoto()).into(holder.postCover);

		TagViewUtils tagViewUtils = new TagViewUtils();
		tagViewUtils.setTagViewParams(post.getChannel().getCategory(), holder.category);

		TimeUtils timeUtils = new TimeUtils();
		holder.readingTime.setText(context.getString(R.string.reading_time, timeUtils.parseReadTimeToMinutes(post
				.getReadTime())));

		boolean postLiked = UserActionsPreferences.getVotes(context).contains(post.getId());
		holder.like.setText(String.valueOf(post.getVotesCount()));
		final VoteHandler voteHandler = new VoteHandler(context);
		voteHandler.changeButtonViewStyle(holder.like, postLiked);

		holder.containerChannel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ChannelActivity.Companion.startActivity(context, post.getChannel());
			}
		});
		holder.timeAdded.setReferenceTime(post.getDateCreatedAsTimestamp());
		holder.postTitle.setText(post.getTitle());
		holder.channelName.setText(post.getChannel().getName());
		holder.layout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onItemClick(post, position);
			}
		});
	}

	static class ViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.container_post_channel_info)
		ViewGroup containerChannel;

		@BindView(R.id.image_channel_avatar)
		ImageView avatar;

		@BindView(R.id.image_post_item_cover)
		ImageView postCover;

		@BindView(R.id.text_name_channel)
		TextView channelName;

		@BindView(R.id.button_like_channel)
		TextView like;

		@BindView(R.id.text_time_added_channel)
		RelativeTimeTextView timeAdded;

		@BindView(R.id.text_category_post)
		TextView category;

		@BindView(R.id.text_title_post)
		TextView postTitle;

		@BindView(R.id.text_reading_time_post)
		TextView readingTime;

		@BindView(R.id.layout_item_post)
		CardView layout;

		private ViewHolder(View view) {
			super(view);
			ButterKnife.bind(this, view);
		}
	}
}