package com.chrono.src.common.utils;

import android.view.View;

public class VisibilityUtils {

	private VisibilityUtils() {
	}

	public static void show(boolean show, View view) {
		if (show) {
			show(view);
		} else {
			hide(view);
		}
	}

	public static void show(View view) {
		view.setVisibility(View.VISIBLE);
	}

	public static void show(View... views) {
		for (View view : views) {
			show(view);
		}
	}

	public static void show(boolean show, View... views) {
		for (View view : views) {
			show(show, view);
		}
	}

	public static void hide(View... views) {
		for (View view : views) {
			hide(view);
		}
	}

	public static void hide(View view) {
		view.setVisibility(View.GONE);
	}
}