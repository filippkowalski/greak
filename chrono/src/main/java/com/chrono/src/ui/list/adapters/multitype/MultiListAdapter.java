package com.chrono.src.ui.list.adapters.multitype;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class MultiListAdapter<X> extends BaseAdapter {

	private MultiTypeManager<X> manager;

	public MultiListAdapter(List<? extends X> data) {
		manager = new MultiTypeManager<>(new ArrayList<>(data));
	}

	public MultiTypeManager<X> getManager() {
		return manager;
	}

	@Override
	public int getCount() {
		return manager.getCount();
	}

	@Override
	public X getItem(int position) {
		return manager.getItem(position);
	}

	@Override
	public int getItemViewType(int position) {
		return manager.getItemViewType(position);
	}

	@Override
	public int getViewTypeCount() {
		return manager.getNumberOfItemTypes();
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		RecyclerView.ViewHolder viewHolder;
		View root = convertView;
		if (root == null) {
			viewHolder = manager.getViewHolder(parent, getItemViewType(position));
			root = viewHolder.itemView;
			root.setTag(viewHolder);
		} else {
			viewHolder = (RecyclerView.ViewHolder) root.getTag();
		}

		manager.populateViewHolderInternally(viewHolder, position);
		return root;
	}
}