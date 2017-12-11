package com.greak.ui;

import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.greak.R;
import com.greak.data.database.UserManager;

import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class GreakApplication extends MultiDexApplication {

	@Override
	public void onCreate() {
		super.onCreate();

		CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
				.setFontAttrId(R.attr.fontPath)
				.build()
		);

//		if (!BuildConfig.DEBUG) {
			Fabric.with(this, new Answers(), new Crashlytics());
//		}

		UserManager userManager = new UserManager(this);
		userManager.initializeUser();
	}
}