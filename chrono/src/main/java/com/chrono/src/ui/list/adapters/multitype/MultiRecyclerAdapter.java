package com.chrono.src.ui.list.adapters.multitype;


import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MultiRecyclerAdapter<X> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private MultiTypeManager<X> manager;

	public MultiRecyclerAdapter(List<X> data) {
		manager = new MultiTypeManager<>(new ArrayList<>(data));
	}

	public MultiTypeManager<X> getManager() {
		return manager;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
		return manager.getViewHolder(parent, viewType);
	}

	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
		manager.populateViewHolderInternally(holder, position);
	}

	@Override
	public int getItemViewType(final int position) {
		return manager.getItemViewType(position);
	}

	@Override
	public int getItemCount() {
		return manager.getCount();
	}
}
