package com.chrono.src.ui.list.adapters.multitype;


import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class MultiTypeManager<X> {

	@Getter
	@Setter
	protected List<X> data;
	private int currentIndex;

	private HashMap<Class, MultiTypeAdapter> factories = new HashMap<>();
	private HashMap<Class, Integer> factoriesIndexed = new HashMap<>();
	private SparseArray<Class> factoriesIndexedReversed = new SparseArray<>();

	public MultiTypeManager(final List<X> data) {
		this.data = data;
	}

	public <T, U extends RecyclerView.ViewHolder> void addRowType(Class<T> type,
	                                                              MultiTypeAdapter<U, ? super T> factory) {
		factories.put(type, factory);
		factoriesIndexed.put(type, currentIndex);
		factoriesIndexedReversed.put(currentIndex, type);
		currentIndex++;
	}

	public int getCount() {
		return data.size();
	}

	public X getItem(int position) {
		return data.get(position);
	}

	public int getItemViewType(int position) {
		Class classOfObject = data.get(position).getClass();
		Integer indexedType = factoriesIndexed.get(classOfObject);

		if (indexedType == null) {
			throw new IllegalStateException("Used unindexed type of data" + classOfObject);
		} else {
			return indexedType;
		}
	}

	public int getNumberOfItemTypes() {
		return currentIndex;
	}

	protected void populateViewHolderInternally(RecyclerView.ViewHolder viewHolder, int position) {
		X dataObj = data.get(position);
		MultiTypeAdapter factory = factories.get(dataObj.getClass());

		if (factory != null) {
			factory.populateViewHolder(viewHolder, position, dataObj);
		} else {
			throw new IllegalStateException("Binding to not registered type of data");
		}
	}

	protected RecyclerView.ViewHolder getViewHolder(ViewGroup viewGroup, int type) {
		Class classOfType = factoriesIndexedReversed.get(type);
		MultiTypeAdapter factory = factories.get(classOfType);

		return factory.getViewHolder(viewGroup);
	}
}
