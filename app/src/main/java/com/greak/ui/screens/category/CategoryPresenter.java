package com.greak.ui.screens.category;

import com.chrono.src.ui.list.ListViewLogic;
import com.chrono.src.ui.list.endless.EndlessListPresenter;
import com.greak.data.models.SteemAccount;
import com.greak.data.models.SteemAccount;

public class CategoryPresenter extends EndlessListPresenter<SteemAccount> {

	private long categoryId;

	CategoryPresenter(long categoryId, ListViewLogic<SteemAccount> contract) {
		super(contract);
		this.categoryId = categoryId;
	}

	@Override
	public void downloadDataFromApi(int offset, boolean showLoadingView) {
		super.downloadDataFromApi(offset, showLoadingView);

//		call = RestService.getInstance()
//				.getApiService()
//				.getChannelsByCategory(categoryId);
//		call.enqueue(getCallback());
	}

}