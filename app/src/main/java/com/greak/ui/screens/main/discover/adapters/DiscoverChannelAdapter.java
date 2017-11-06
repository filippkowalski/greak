package com.greak.ui.screens.main.discover.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chrono.src.ui.list.OnItemClickListener;
import com.chrono.src.ui.list.adapters.multitype.MultiTypeAdapter;
import com.greak.R;
import com.greak.data.models.Channel;
import com.greak.ui.common.TagViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiscoverChannelAdapter implements MultiTypeAdapter<DiscoverChannelAdapter.ViewHolder, Channel> {

	private Context context;
	private OnItemClickListener<Channel> listener;

	public DiscoverChannelAdapter(Context context, OnItemClickListener<Channel> listener) {
		this.context = context;
		this.listener = listener;
	}

	@Override
	public void populateViewHolder(ViewHolder holder, final int position, final Channel channel) {
		holder.layoutContainer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				listener.onItemClick(channel, position);
			}
		});

		TagViewUtils tagViewUtils = new TagViewUtils();
		tagViewUtils.setTagViewParams(channel   .getCategory(), holder.tag);

		holder.title.setText(channel.getName());
		holder.description.setText(channel.getDescription());
		Glide.with(context).load(channel.getCoverPhoto()).into(holder.cover);
	}

	@Override
	public ViewHolder getViewHolder(ViewGroup viewGroup) {
		View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_channel, viewGroup, false);
		return new ViewHolder(view);
	}

	static class ViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.layout_item_channel)
		ViewGroup layoutContainer;

		@BindView(R.id.text_tag_channel)
		TextView tag;

		@BindView(R.id.text_title_item_channel)
		TextView title;

		@BindView(R.id.text_description_item_channel)
		TextView description;

		@BindView(R.id.image_cover_item_channel)
		ImageView cover;

		private ViewHolder(View view) {
			super(view);
			ButterKnife.bind(this, view);
		}
	}
}