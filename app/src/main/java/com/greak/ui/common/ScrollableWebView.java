package com.greak.ui.common;

import android.content.Context;
import android.util.AttributeSet;

import lombok.Getter;
import us.feras.mdv.MarkdownView;

public class ScrollableWebView extends MarkdownView {

	@Getter
	private OnScrollChangedCallback mOnScrollChangedCallback;

	public ScrollableWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ScrollableWebView(Context context) {
		super(context);
	}

	@Override
	protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
		super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
		if (mOnScrollChangedCallback != null) {
			mOnScrollChangedCallback.onScroll(scrollY, clampedY);
		}
	}

	public void setOnScrollChangedCallback(final OnScrollChangedCallback onScrollChangedCallback) {
		mOnScrollChangedCallback = onScrollChangedCallback;
	}

	/**
	 * Implement in the activity/fragment/view that you want to listen to the webview
	 */
	public interface OnScrollChangedCallback {
		void onScroll(int scrollY, boolean clampedY);
	}
}