package com.greak.ui.common;


import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.greak.R;

public class ToolbarInitializer {

	public static Toolbar initToolbarWithTitle(AppCompatActivity activity, String title, boolean enableBackOption) {
		Toolbar toolbar = initToolbar(activity, enableBackOption);
		toolbar.setTitle(title);

		return toolbar;
	}

	public static Toolbar initToolbarWithLogo(AppCompatActivity activity, boolean enableBackOption) {
		return initToolbar(activity, enableBackOption);
	}

	private static Toolbar initToolbar(AppCompatActivity activity, boolean enableBackOption) {
		Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
		activity.setSupportActionBar(toolbar);
		activity.getSupportActionBar().setDisplayHomeAsUpEnabled(enableBackOption);
		activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
		return toolbar;
	}
}