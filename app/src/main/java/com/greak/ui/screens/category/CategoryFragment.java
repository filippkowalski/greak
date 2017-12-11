package com.greak.ui.screens.category;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.chrono.src.ui.list.OnItemClickListener;
import com.chrono.src.ui.list.adapters.decoration.DefaultSpacingItemDecoration;
import com.chrono.src.ui.list.endless.EndlessListFragment;
import com.greak.common.utils.StatsConstants;
import com.greak.data.models.Category;
import com.greak.data.models.SteemAccount;
import com.greak.ui.analytics.FabricAnalyticsManager;

import java.util.List;

public class CategoryFragment extends EndlessListFragment<SteemAccount, SteemAccount, ChannelMultiAdapter> implements
		OnItemClickListener<SteemAccount> {

	private static final String BUNDLE_CATEGORY = "category";
	private Category category;

	public static CategoryFragment newInstance(Category category) {
		Bundle args = new Bundle();
		args.putParcelable(BUNDLE_CATEGORY, category);
		CategoryFragment fragment = new CategoryFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public CategoryPresenter createPresenter() {
		category = getArguments().getParcelable(BUNDLE_CATEGORY);
		FabricAnalyticsManager.logEvent(StatsConstants.CATEGORY, category.getName());

		return new CategoryPresenter(category.getId(), this);
	}

	@Override
	public ChannelMultiAdapter createAdapter(List<SteemAccount> data) {
		return new ChannelMultiAdapter(getContext(), data, this);
	}

	@Override
	protected void addItemsToAdapter(List<SteemAccount> data, boolean firstRun) {
		getAdapter().addItems(data, firstRun);
	}

	@Override
	public void onItemClick(SteemAccount steemAccount, int position) {
		// TODO no all data is available
//		UserProfileActivity.Companion.startActivity(getContext(), steemAccount);
	}

	@Override
	protected RecyclerView.ItemDecoration getItemDecorator() {
		return new DefaultSpacingItemDecoration(0);
	}
}