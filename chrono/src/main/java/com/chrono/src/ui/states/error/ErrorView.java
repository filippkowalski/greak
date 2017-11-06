package com.chrono.src.ui.states.error;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;

public abstract class ErrorView extends CoordinatorLayout {

	public ErrorView(Context context) {
		super(context);
	}

	public ErrorView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	public ErrorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public abstract void setError(@ErrorTypeGenerator.ErrorType int errorType);
}