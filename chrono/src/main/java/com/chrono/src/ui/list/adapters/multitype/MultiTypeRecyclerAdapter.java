package com.chrono.src.ui.list.adapters.multitype;


import android.support.annotation.NonNull;

import com.chrono.src.ui.list.adapters.headerFooterAdapter.RecyclerViewLogic;

import java.util.Collection;
import java.util.List;

public class MultiTypeRecyclerAdapter<D> extends MultiRecyclerAdapter<D> implements RecyclerViewLogic<D> {

	public MultiTypeRecyclerAdapter(final List<D> data) {
		super(data);
	}

	@Override
	public D getItem(final int position) {
		return getManager().getItem(position);
	}

	@Override
	public void addItem(final D item, final int position) {
		getManager().getData().add(position, item);
		notifyItemInserted(position);
	}

	@Override
	public List<D> getItems() {
		return getManager().getData();
	}

	@Override
	public void addItems(final Collection<? extends D> items, final boolean firstRun) {
		if (firstRun) {
			clearAllItems();
		}

		List<D> genericData = getManager().getData();
		int size = genericData.size();
		genericData.addAll(items);
		notifyItemRangeInserted(size, items.size());
	}

	@Override
	public void clearAllItems() {
		getManager().getData().clear();
		notifyDataSetChanged();
	}

	@Override
	public void removeItemsByType(@NonNull Class item) {
		List<D> itemList = getItems();
		for (int i = 0; i < itemList.size(); i++) {
			if (item.isInstance(itemList.get(i))) {
				itemList.remove(i);
				notifyItemRemoved(i);
				i--;
			}
		}
	}
}
