package com.greak.ui;

import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.greak.BuildConfig;
import com.greak.R;
import com.greak.data.database.UserManager;

import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Filip Kowalski on 25.02.17.
 */

public class GreakApplication extends MultiDexApplication {

	@Override
	public void onCreate() {
		super.onCreate();

		CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
				.setFontAttrId(R.attr.fontPath)
				.build()
		);

		if (!BuildConfig.DEBUG) {
			Fabric.with(this, new Crashlytics());
		}

		UserManager userManager = new UserManager(this);
		userManager.initializeUser();
	}
}