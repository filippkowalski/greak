package com.greak.ui.screens.category;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.chrono.src.ui.list.OnItemClickListener;
import com.chrono.src.ui.list.adapters.decoration.DefaultSpacingItemDecoration;
import com.chrono.src.ui.list.endless.EndlessListFragment;
import com.greak.common.utils.StatsConstants;
import com.greak.data.models.Category;
import com.greak.data.models.Channel;
import com.greak.ui.analytics.FabricAnalyticsManager;
import com.greak.ui.screens.channel.ChannelActivity;

import java.util.List;

public class CategoryFragment extends EndlessListFragment<Channel, Channel, ChannelMultiAdapter> implements
		OnItemClickListener<Channel> {

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
	public ChannelMultiAdapter createAdapter(List<Channel> data) {
		return new ChannelMultiAdapter(getContext(), data, this);
	}

	@Override
	protected void addItemsToAdapter(List<Channel> data, boolean firstRun) {
		getAdapter().addItems(data, firstRun);
	}

	@Override
	public void onItemClick(Channel channel, int position) {
		ChannelActivity.Companion.startActivity(getContext(), channel);
	}

	@Override
	protected RecyclerView.ItemDecoration getItemDecorator() {
		return new DefaultSpacingItemDecoration(0);
	}
}