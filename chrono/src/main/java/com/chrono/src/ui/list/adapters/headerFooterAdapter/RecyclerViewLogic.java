package com.chrono.src.ui.list.adapters.headerFooterAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.Collection;
import java.util.List;

public interface RecyclerViewLogic<T> {

    RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int type);

    void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position);

    int getItemCount();

    int getItemViewType(int position);

    T getItem(int position);

    void addItem(T item, int position);

    void clearAllItems();

    void removeItemsByType(Class clazz);

    List<T> getItems();

    void addItems(Collection<? extends T> items, boolean initial);

    void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer);

    void unregisterAdapterDataObserver(RecyclerView.AdapterDataObserver observer);
}
