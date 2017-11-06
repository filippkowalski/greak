package com.greak.ui.screens.main.common;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class PostSpacingItemDecoration extends RecyclerView.ItemDecoration {

	private int topListSpacing;
	private int sidesListSpacing;

	public PostSpacingItemDecoration(int topListSpacing, int sidesListSpacing) {
		this.topListSpacing = topListSpacing;
		this.sidesListSpacing = sidesListSpacing;
	}

	public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
		outRect.top = this.topListSpacing;
		outRect.left = this.sidesListSpacing;
		outRect.right = this.sidesListSpacing;
	}
}