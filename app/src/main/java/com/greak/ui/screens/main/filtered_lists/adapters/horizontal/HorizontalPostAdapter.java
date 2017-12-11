package com.greak.ui.screens.main.filtered_lists.adapters.horizontal;

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
import com.greak.R;
import com.greak.data.models.Post;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HorizontalPostAdapter extends RecyclerView.Adapter<HorizontalPostAdapter.ViewHolder> {

	private Context context;
	private List<Post> posts = new ArrayList<>();
	private OnItemClickListener<Post> listener;

	public HorizontalPostAdapter(Context context, List<Post> posts, OnItemClickListener<Post> listener) {
		this.context = context;
		this.posts = posts;
		this.listener = listener;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_horizontal, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, final int position) {
		final Post post = posts.get(position);

		Glide.with(context).load(post.getCoverPhoto()).into(holder.cover);
		holder.count.setText(String.valueOf(post.getVotesCount()));
		holder.title.setText(post.getTitle());
		holder.layout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onItemClick(post, position);
			}
		});
	}

	@Override
	public int getItemCount() {
		return posts.size();
	}

	static class ViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.image_post_element_cover)
		ImageView cover;

		@BindView(R.id.text_post_element_count)
		TextView count;

		@BindView(R.id.text_post_element_title)
		TextView title;

		@BindView(R.id.layout_item_horizontal)
		CardView layout;

		private ViewHolder(View view) {
			super(view);
			ButterKnife.bind(this, view);
		}
	}
}