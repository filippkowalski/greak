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
import com.greak.data.models.Categories;
import com.greak.data.models.Category;
import com.greak.ui.screens.main.discover.adapters.horizontal.HorizontalCategoryAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiscoverCategoriesAdapter implements MultiTypeAdapter<DiscoverCategoriesAdapter.ViewHolder, Categories> {

	private Context context;
	private OnItemClickListener<Category> listener;

	public DiscoverCategoriesAdapter(Context context, OnItemClickListener<Category> listener) {
		this.context = context;
		this.listener = listener;
	}

	@Override
	public void populateViewHolder(ViewHolder holder, int i, Categories categories) {
		holder.category.setText(categories.getHeaderTitle());
		holder.horizontalList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
		holder.horizontalList.setAdapter(new HorizontalCategoryAdapter(context, categories.getCategories(), listener));
	}

	@Override
	public ViewHolder getViewHolder(ViewGroup viewGroup) {
		View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_categories, viewGroup, false);
		return new ViewHolder(view);
	}

	static class ViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.recycler_view_horizontal_category)
		RecyclerView horizontalList;

		@BindView(R.id.text_title_item_category)
		TextView category;

		private ViewHolder(View view) {
			super(view);
			ButterKnife.bind(this, view);
		}
	}
}