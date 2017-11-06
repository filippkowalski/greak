package com.greak;

import android.app.Activity;
import android.os.Build;

import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;
import com.greak.ui.screens.main.MainActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by Filip Kowalski on 7/20/17.
 */

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP_MR1)
@RunWith(RobolectricTestRunner.class)
public class NavigationBarTest {

	private Activity activity;

	@Before
	public void setup() {
		activity = Robolectric.buildActivity(MainActivity.class).create().resume().get();
	}


	@Test
	public void validateTextViewContent() {
		BottomNavigationView bottomNavigationView = (BottomNavigationView) activity.findViewById(R.id.navigation_bottom_main);

		assertNotNull("Bottom Navigation is null", bottomNavigationView);
	}

}
