package com.greak.ui.screens.main.votes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.greak.R;
import com.greak.common.utils.StatsConstants;
import com.greak.ui.analytics.FabricAnalyticsManager;
import com.greak.ui.base.BaseActivity;
import com.greak.ui.screens.main.common.OnRefreshListener;

import butterknife.ButterKnife;

public class VotesActivity extends BaseActivity implements OnRefreshListener {

	public static void startActivity(Context context) {
		Intent intent = new Intent(context, VotesActivity.class);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_fragment_container_without_toolbar);
		ButterKnife.bind(this);
		FabricAnalyticsManager.logEvent(StatsConstants.VOTES, null);

		initFragment();
	}

	private void initFragment() {
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.container_fragment, VotesListFragment.newInstance())
				.commit();
	}

	@Override
	public void onRefresh() {
		initFragment();
	}
}