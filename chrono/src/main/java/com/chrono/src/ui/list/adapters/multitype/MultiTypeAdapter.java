package com.chrono.src.ui.list.adapters.multitype;


import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public interface MultiTypeAdapter<VH extends RecyclerView.ViewHolder, D> {

    void populateViewHolder(VH viewHolder, int position, D item);
    VH getViewHolder(ViewGroup viewGroup);
}