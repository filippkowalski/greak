package com.greak.common.utils;

import com.crashlytics.android.Crashlytics;
import com.greak.BuildConfig;

public class Logger {

	public static void logException(Throwable e) {
		if (BuildConfig.DEBUG) {
			e.printStackTrace();
		}
		Crashlytics.logException(e);
	}
}
