package com.greak.ui.screens.main;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.greak.R;
import com.greak.common.utils.StatsConstants;
import com.greak.data.database.UserInstance;
import com.greak.ui.analytics.FabricAnalyticsManager;
import com.greak.ui.base.BaseActivity;
import com.greak.ui.screens.main.common.OnRefreshListener;
import com.greak.ui.views.NonSlidingViewPager;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;
import com.luseen.luseenbottomnavigation.BottomNavigation.OnBottomNavigationItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import hugo.weaving.DebugLog;

public class MainActivity extends BaseActivity implements OnBottomNavigationItemClickListener, OnRefreshListener {

	@BindView(R.id.viewpager_main)
	protected NonSlidingViewPager viewPager;

	@BindView(R.id.navigation_bottom_main)
	protected BottomNavigationView navigationBottom;
	private MainViewPagerAdapter pagerAdapter;

	public static void startActivity(Context context) {
		Intent intent = new Intent(context, MainActivity.class);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);

		initViewPager();
		initBottomNavigation();
	}

	@Override
	protected void onResume() {
		super.onResume();

		UserInstance userInstance = UserInstance.getInstance();
		if (userInstance.isFreshLogin()) {
			userInstance.setFreshLogin(false);
			restartActivity();
		} else {
			viewPager.setCurrentItem(navigationBottom.getCurrentItem());
		}
	}

	private void initViewPager() {
		pagerAdapter = new MainViewPagerAdapter(this, getSupportFragmentManager());
		viewPager.setAdapter(pagerAdapter);
		viewPager.setOffscreenPageLimit(2);
		viewPager.setPagingEnabled(false);
	}

	private void initBottomNavigation() {
		int selectedColor = ContextCompat.getColor(this, R.color.color_accent);
		int[] color = {selectedColor, selectedColor, selectedColor, selectedColor};
		int[] image = {R.drawable.ic_star_white_24dp, R.drawable.ic_fiber_new_white_24dp,
				R.drawable.ic_whatshot_white_24dp, R.drawable.ic_person_white_24dp};

		navigationBottom.setUpWithViewPager(viewPager, color, image);
		navigationBottom.setOnBottomNavigationItemClickListener(this);
		navigationBottom.disableShadow();

		if (!UserInstance.getInstance().isLogged()) {
			navigationBottom.selectTab(1);
		}
	}

	@Override
	public void onNavigationItemClick(int position) {
		if (viewPager.getCurrentItem() == navigationBottom.getCurrentItem()) {
			pagerAdapter.handleSecondaryClick(position);
		} else {
			FabricAnalyticsManager.logEvent(StatsConstants.NAVIGATION_BOTTOM, (String) pagerAdapter.getPageTitle(position));
		}
	}

	@DebugLog
	@Override
	public void onRefresh() {
		restartActivity();
	}

	private void restartActivity() {
		finish();
		MainActivity.startActivity(this);
	}
}