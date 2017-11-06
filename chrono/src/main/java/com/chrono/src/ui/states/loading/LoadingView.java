package com.chrono.src.ui.states.loading;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chrono.R;

public class LoadingView extends LinearLayout {

	public LoadingView(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		setOrientation(VERTICAL);
		setGravity(Gravity.CENTER);
		ViewGroup.LayoutParams layoutParams =
				new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		setLayoutParams(layoutParams);
		View view = View.inflate(context, R.layout.layout_loading, this);

		view.setBackgroundColor(Color.WHITE);
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// do nothing, just fetch click
			}
		});
	}
}