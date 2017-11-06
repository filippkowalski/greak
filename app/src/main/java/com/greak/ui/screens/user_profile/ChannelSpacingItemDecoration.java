package com.greak.ui.screens.user_profile;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ChannelSpacingItemDecoration extends RecyclerView.ItemDecoration {

	private int topListSpacing;
	private int sidesListSpacing;

	public ChannelSpacingItemDecoration(int topListSpacing, int sidesListSpacing) {
		this.topListSpacing = topListSpacing;
		this.sidesListSpacing = sidesListSpacing;
	}

	public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
		if (parent.getChildAdapterPosition(view) != 0) {
			outRect.top = this.topListSpacing;
			outRect.left = this.sidesListSpacing;
			outRect.right = this.sidesListSpacing;
		}
	}
}