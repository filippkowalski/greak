package com.greak.ui.screens.main.discover.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chrono.src.ui.list.OnItemClickListener;
import com.chrono.src.ui.list.adapters.multitype.MultiTypeAdapter;
import com.greak.R;
import com.greak.data.models.Channel;
import com.greak.data.models.Channels;
import com.greak.ui.screens.main.discover.adapters.horizontal.HorizontalChannelAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiscoverChannelsAdapter implements MultiTypeAdapter<DiscoverChannelsAdapter.ViewHolder, Channels> {

	private Context context;
	private OnItemClickListener<Channel> listener;

	public DiscoverChannelsAdapter(Context context, OnItemClickListener<Channel> listener) {
		this.context = context;
		this.listener = listener;
	}

	@Override
	public void populateViewHolder(ViewHolder holder, int i, Channels channels) {
		holder.title.setText(channels.getHeaderTitle());
		holder.horizontalList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
		holder.horizontalList.setAdapter(new HorizontalChannelAdapter(context, channels.getChannels(), listener));
	}

	@Override
	public ViewHolder getViewHolder(ViewGroup viewGroup) {
		View view = LayoutInflater.from(viewGroup.getContext())
				.inflate(R.layout.item_horizontal_list_container, viewGroup, false);
		return new ViewHolder(view);
	}

	static class ViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.text_title_item_horizontal)
		TextView title;

		@BindView(R.id.recycler_view_horizontal_list)
		RecyclerView horizontalList;

		private ViewHolder(View view) {
			super(view);
			ButterKnife.bind(this, view);
		}
	}
}