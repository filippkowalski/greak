package com.greak.ui.screens.main.subscriptions;

import com.chrono.src.common.constants.ConfigConstants;
import com.chrono.src.ui.list.ListViewLogic;
import com.chrono.src.ui.list.endless.EndlessListPresenter;
import com.greak.data.database.UserInstance;
import com.greak.data.models.Account;
import com.greak.data.models.Post;
import com.greak.data.rest.RestService;

class SubscriptionsPresenter extends EndlessListPresenter<Post> {

	SubscriptionsPresenter(ListViewLogic<Post> contract) {
		super(contract);
	}

	@Override
	public void downloadDataFromApi(int offset, boolean showLoadingView) {
		super.downloadDataFromApi(offset, showLoadingView);

		Account account = UserInstance.getInstance().getAccount();
		String username = account != null ? account.getUsername() : null;
		call = RestService.getInstance()
				.getApiService()
				.getUserFollowedPosts(username, offset, ConfigConstants.LIST_LOAD_LIMIT);
		call.enqueue(getCallback());
	}
}