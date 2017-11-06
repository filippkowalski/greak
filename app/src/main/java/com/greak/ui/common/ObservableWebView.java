package com.greak.ui.common;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import lombok.Getter;

public class ObservableWebView extends WebView {

	@Getter
	private OnScrollChangedCallback mOnScrollChangedCallback;

	public ObservableWebView(final Context context) {
		super(context);
	}

	public ObservableWebView(final Context context, final AttributeSet attrs) {
		super(context, attrs);
	}

	public ObservableWebView(final Context context, final AttributeSet attrs, final int defStyle) {
		super(context, attrs, defStyle);
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