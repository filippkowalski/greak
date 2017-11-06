package com.greak.ui.screens.main.discover.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chrono.src.ui.list.OnItemClickListener;
import com.chrono.src.ui.list.adapters.multitype.MultiTypeAdapter;
import com.greak.R;
import com.greak.data.models.Post;
import com.greak.data.models.Posts;
import com.greak.ui.screens.main.discover.adapters.horizontal.HorizontalPostAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiscoverPostsAdapter implements MultiTypeAdapter<DiscoverPostsAdapter.ViewHolder, Posts> {

	private Context context;
	private OnItemClickListener<Post> listener;

	public DiscoverPostsAdapter(Context context, OnItemClickListener<Post> listener) {
		this.context = context;
		this.listener = listener;
	}

	@Override
	public void populateViewHolder(ViewHolder holder, int i, Posts posts) {
		holder.horizontalList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
		holder.horizontalList.setAdapter(new HorizontalPostAdapter(context, posts.getPosts(), listener));
	}

	@Override
	public ViewHolder getViewHolder(ViewGroup viewGroup) {
		View view = LayoutInflater.from(viewGroup.getContext())
				.inflate(R.layout.item_horizontal_list_container, viewGroup, false);
		return new ViewHolder(view);
	}

	static class ViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.recycler_view_horizontal_list)
		RecyclerView horizontalList;

		private ViewHolder(View view) {
			super(view);
			ButterKnife.bind(this, view);
		}
	}
}