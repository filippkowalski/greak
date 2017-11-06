package com.greak.ui.screens.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.chrono.src.common.constants.StringConstants;
import com.greak.R;
import com.greak.ui.screens.main.common.DirectFragmentStatePagerAdapter;
import com.greak.ui.screens.main.common.ScrollableToTop;
import com.greak.ui.screens.main.discover.DiscoverListFragment;
import com.greak.ui.screens.main.subscriptions.SubscriptionsListFragment;
import com.greak.ui.screens.profile.ProfileFragment;

public class MainViewPagerAdapter extends DirectFragmentStatePagerAdapter {

	public static final int SUBSCRIPTIONS_POSITION = 0;
	public static final int DISCOVERY_POSITION = 1;
	public static final int PROFILE_POSITION = 2;

	private Context context;

	public MainViewPagerAdapter(Context context, FragmentManager fm) {
		super(fm);
		this.context = context;
	}

	@Override
	public Fragment getItem(int position) {
		Fragment fragment = null;
		switch (position) {
			case SUBSCRIPTIONS_POSITION:
				fragment = SubscriptionsListFragment.newInstance();
				break;
			case DISCOVERY_POSITION:
				fragment = DiscoverListFragment.newInstance();
				break;
			case PROFILE_POSITION:
				fragment = ProfileFragment.Companion.newInstance();
				break;
		}

		return fragment;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		if (position == DISCOVERY_POSITION) {
			return context.getString(R.string.discover);
		} else if (position == SUBSCRIPTIONS_POSITION) {
			return context.getString(R.string.observed);
		} else if (position == PROFILE_POSITION) {
			return context.getString(R.string.profile);
		} else {
			return StringConstants.EMPTY;
		}
	}

	@Override
	public int getCount() {
		return 3;
	}

	void handleSecondaryClick(int position) {
		Fragment instantiatedFragment = getCreatedFragment(position);

		if (instantiatedFragment instanceof ScrollableToTop) {
			((ScrollableToTop) instantiatedFragment).scrollToTop();
		}
	}
}