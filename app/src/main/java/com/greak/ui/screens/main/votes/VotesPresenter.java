package com.greak.ui.screens.main.votes;

import com.chrono.src.common.constants.ConfigConstants;
import com.chrono.src.ui.list.ListViewLogic;
import com.chrono.src.ui.list.endless.EndlessListPresenter;
import com.greak.data.models.Post;
import com.greak.data.rest.RestService;

class VotesPresenter extends EndlessListPresenter<Post> {

	VotesPresenter(ListViewLogic<Post> contract) {
		super(contract);
	}

	@Override
	public void downloadDataFromApi(int offset, boolean showLoadingView) {
		super.downloadDataFromApi(offset, showLoadingView);

		call = RestService.getInstance()
				.getApiService()
				.getFavorites(offset, ConfigConstants.LIST_LOAD_LIMIT);
		call.enqueue(getCallback());
	}
}