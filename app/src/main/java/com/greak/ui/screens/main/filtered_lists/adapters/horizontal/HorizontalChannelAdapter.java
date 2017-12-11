package com.greak.ui.screens.main.filtered_lists.adapters.horizontal;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chrono.src.ui.list.OnItemClickListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.greak.R;
import com.greak.data.models.SteemAccount;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HorizontalChannelAdapter extends RecyclerView.Adapter<HorizontalChannelAdapter.ViewHolder> {

	private Context context;
	private List<SteemAccount> steemAccounts = new ArrayList<>();
	private OnItemClickListener<SteemAccount> listener;

	public HorizontalChannelAdapter(Context context, List<SteemAccount> steemAccounts, OnItemClickListener<SteemAccount>
			listener) {
		this.context = context;
		this.steemAccounts = steemAccounts;
		this.listener = listener;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_channel_horizontal, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, final int position) {
		final SteemAccount steemAccount = steemAccounts.get(position);

		Glide.with(context).load(steemAccount.getCoverPhoto()).into(holder.cover);
		holder.name.setText(steemAccount.getName());
		holder.description.setText(steemAccount.getDescription());
		holder.layout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onItemClick(steemAccount, position);
			}
		});
	}

	@Override
	public int getItemCount() {
		return steemAccounts.size();
	}

	static class ViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.image_post_element_cover)
		RoundedImageView cover;

		@BindView(R.id.text_channel_name_horizontal_item)
		TextView name;

		@BindView(R.id.text_channel_description_horizontal_item)
		TextView description;

		@BindView(R.id.layout_item_horizontal)
		CardView layout;

		private ViewHolder(View view) {
			super(view);
			ButterKnife.bind(this, view);
		}
	}
}