package com.greak.ui;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.greak.BuildConfig;
import com.greak.R;
import com.greak.data.database.UserActionsPreferences;
import com.greak.data.database.UserManager;
import com.greak.service.tasks.SynchronizationService;
import com.greak.ui.common.TimeUtils;

import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Filip Kowalski on 25.02.17.
 */

public class GreakApplication extends Application {

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


		TimeUtils timeUtils = new TimeUtils();
		if (timeUtils.has24hoursPassed(this)) {
			SynchronizationService synchronizationService = new SynchronizationService();
			synchronizationService.synchronize(this);

			UserActionsPreferences.setSynchronizationTime(this, System.currentTimeMillis());
		}
	}
}