package com.greak.ui.screens.post;

import android.content.Context;
import android.support.annotation.NonNull;
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
import com.greak.common.utils.CurrencyUtils;
import com.greak.data.database.UserActionsPreferences;
import com.greak.data.models.Post;
import com.greak.ui.common.TagViewUtils;
import com.greak.ui.screens.post.clickhandlers.VoteHandler;
import com.greak.ui.screens.user_profile.UserProfileActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

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
	public void populateViewHolder(@NonNull ViewHolder holder, final int position, final Post post) {
		Glide.with(context).load(post.getSteemAccount().getAvatar()).placeholder(R.drawable.ic_steem).into(holder.avatar);
		Glide.with(context).load(post.getCoverPhoto()).into(holder.postCover);

		TagViewUtils tagViewUtils = new TagViewUtils();
		tagViewUtils.setTagViewParams(post.getCategory(), holder.category);

		boolean postLiked = UserActionsPreferences.getVotes(context).contains(post.getId());
		holder.earnedMoney.setText(CurrencyUtils.formatCurrency(post.getMoneyEarned()));
		final VoteHandler voteHandler = new VoteHandler(context);
		voteHandler.changeButtonViewStyle(holder.earnedMoney, postLiked);

		holder.containerChannel.setOnClickListener(v -> UserProfileActivity.Companion.startActivity(context, post.getSteemAccount()));
		holder.timeAdded.setReferenceTime(post.getDateCreatedAsTimestamp());
		holder.postTitle.setText(post.getTitle());
		holder.authorName.setText(post.getSteemAccount().getName());
		holder.readingTime.setText(context.getString(R.string.reading_time, post.getReadTime()));
		holder.layout.setOnClickListener(v -> listener.onItemClick(post, position));
	}

	static class ViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.container_post_channel_info)
		ViewGroup containerChannel;

		@BindView(R.id.image_channel_avatar)
		ImageView avatar;

		@BindView(R.id.image_post_item_cover)
		ImageView postCover;

		@BindView(R.id.text_name_channel)
		TextView authorName;

		@BindView(R.id.button_like_channel)
		TextView earnedMoney;

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