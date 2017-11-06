package com.greak.ui.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Filip Kowalski on 21.05.17.
 */

public class NonSlidingViewPager extends ViewPager {

	private boolean pagingEnabled = true;

	public NonSlidingViewPager(Context context) {
		super(context);
	}

	public NonSlidingViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return this.pagingEnabled && super.onTouchEvent(event);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		return this.pagingEnabled && super.onInterceptTouchEvent(event);
	}

	public void setPagingEnabled(boolean pagingEnabled) {
		this.pagingEnabled = pagingEnabled;
	}
}
