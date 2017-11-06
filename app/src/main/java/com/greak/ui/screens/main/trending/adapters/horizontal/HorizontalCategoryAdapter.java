package com.greak.ui.screens.main.trending.adapters.horizontal;

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
import com.greak.data.models.Category;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HorizontalCategoryAdapter extends RecyclerView.Adapter<HorizontalCategoryAdapter.ViewHolder> {

	private Context context;
	private List<Category> categories = new ArrayList<>();
	private OnItemClickListener<Category> listener;

	public HorizontalCategoryAdapter(Context context, List<Category> categories, OnItemClickListener<Category>
			listener) {
		this.context = context;
		this.categories = categories;
		this.listener = listener;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_horizontal, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, final int position) {
		final Category category = categories.get(position);

		Glide.with(context).load(category.getCoverPhoto()).into(holder.cover);
		holder.name.setText(category.getName());
		holder.layout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onItemClick(category, position);
			}
		});
	}

	@Override
	public int getItemCount() {
		return categories.size();
	}

	static class ViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.image_post_element_cover)
		RoundedImageView cover;

		@BindView(R.id.text_category_name_horizontal_item)
		TextView name;

		@BindView(R.id.layout_item_horizontal)
		CardView layout;

		private ViewHolder(View view) {
			super(view);
			ButterKnife.bind(this, view);
		}
	}
}