package com.greak.ui.screens.category;

import com.chrono.src.ui.list.ListViewLogic;
import com.chrono.src.ui.list.endless.EndlessListPresenter;
import com.greak.data.models.Channel;
import com.greak.data.rest.RestService;

public class CategoryPresenter extends EndlessListPresenter<Channel> {

	private long categoryId;

	CategoryPresenter(long categoryId, ListViewLogic<Channel> contract) {
		super(contract);
		this.categoryId = categoryId;
	}

	@Override
	public void downloadDataFromApi(int offset, boolean showLoadingView) {
		super.downloadDataFromApi(offset, showLoadingView);

		call = RestService.getInstance()
				.getApiService()
				.getChannelsByCategory(categoryId);
		call.enqueue(getCallback());
	}

}