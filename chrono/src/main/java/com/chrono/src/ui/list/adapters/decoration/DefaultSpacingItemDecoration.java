package com.chrono.src.ui.list.adapters.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class DefaultSpacingItemDecoration extends RecyclerView.ItemDecoration {

	private int spacing;

	public DefaultSpacingItemDecoration(int spacing) {
		this.spacing = spacing;
	}

	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
		outRect.left = spacing;
		outRect.right = spacing;
	}
}
