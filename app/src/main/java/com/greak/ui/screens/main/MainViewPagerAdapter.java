package com.greak.ui.screens.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.chrono.src.common.constants.StringConstants;
import com.greak.R;
import com.greak.ui.screens.main.common.DirectFragmentStatePagerAdapter;
import com.greak.ui.screens.main.common.ListType;
import com.greak.ui.screens.main.common.ScrollableToTop;
import com.greak.ui.screens.main.filtered_lists.FilteredListFragment;
import com.greak.ui.screens.main.feed.FeedListFragment;
import com.greak.ui.screens.profile.ProfileFragment;

public class MainViewPagerAdapter extends DirectFragmentStatePagerAdapter {

	private static final int SUBSCRIPTIONS_POSITION = 0;
	private static final int NEW_POSITION = 1;
	private static final int TRENDING_POSITION = 2;
	private static final int PROFILE_POSITION = 3;

	private Context context;

	MainViewPagerAdapter(Context context, FragmentManager fm) {
		super(fm);
		this.context = context;
	}

	@Override
	public Fragment getItem(int position) {
		Fragment fragment = null;
		switch (position) {
			case SUBSCRIPTIONS_POSITION:
				fragment = FeedListFragment.newInstance();
				break;
			case NEW_POSITION:
				fragment = FilteredListFragment.newInstance(ListType.TYPE_NEW);
				break;
			case TRENDING_POSITION:
				fragment = FilteredListFragment.newInstance(ListType.TYPE_TRENDING);
				break;
			case PROFILE_POSITION:
				fragment = ProfileFragment.Companion.newInstance();
				break;
		}

		return fragment;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		switch (position) {
			case NEW_POSITION:
				return context.getString(R.string.new_list);
			case TRENDING_POSITION:
				return context.getString(R.string.trending);
			case SUBSCRIPTIONS_POSITION:
				return context.getString(R.string.followed);
			case PROFILE_POSITION:
				return context.getString(R.string.profile);
			default:
				return StringConstants.EMPTY;
		}
	}

	@Override
	public int getCount() {
		return 4;
	}

	void handleSecondaryClick(int position) {
		Fragment instantiatedFragment = getCreatedFragment(position);

		if (instantiatedFragment instanceof ScrollableToTop) {
			((ScrollableToTop) instantiatedFragment).scrollToTop();
		}
	}
}