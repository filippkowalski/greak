package com.chrono.src.ui.list.adapters.multitype


import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

interface MultiTypeAdapter<VH : RecyclerView.ViewHolder, D> {

    fun populateViewHolder(viewHolder: VH, position: Int, item: D)
    fun getViewHolder(viewGroup: ViewGroup): VH
}