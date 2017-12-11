package com.greak.ui.analytics;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.greak.BuildConfig;

public class FabricAnalyticsManager {

	public static void logEvent(String contentType, String contentName) {
		if (!BuildConfig.DEBUG) {
			Answers.getInstance().logCustom(new CustomEvent(contentType)
					.putCustomAttribute("Content Name", contentName));
		}
	}

	public static void logEvent(String contentType, int contentValue) {
		if (!BuildConfig.DEBUG) {
			Answers.getInstance().logCustom(new CustomEvent(contentType)
					.putCustomAttribute("Content Value", contentValue));
		}
	}
}
