package com.greak.ui.screens.main.discover;

import com.chrono.src.common.constants.ConfigConstants;
import com.chrono.src.ui.list.ListViewLogic;
import com.chrono.src.ui.list.endless.EndlessListPresenter;
import com.greak.data.models.DiscoverItem;
import com.greak.data.rest.RestService;

class DiscoverPresenter extends EndlessListPresenter<DiscoverItem> {

	DiscoverPresenter(ListViewLogic<DiscoverItem> contract) {
		super(contract);
	}

	@Override
	public void downloadDataFromApi(int offset, boolean showLoadingView) {
		super.downloadDataFromApi(offset, showLoadingView);

		call = RestService.getInstance()
				.getApiService()
				.getDiscoverFeed(offset, ConfigConstants.LIST_LOAD_LIMIT);
		call.enqueue(getCallback());
	}

}