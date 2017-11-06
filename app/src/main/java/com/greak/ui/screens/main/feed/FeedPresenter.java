package com.greak.ui.screens.main.feed;

import com.chrono.src.ui.list.ListViewLogic;
import com.chrono.src.ui.list.endless.EndlessListPresenter;
import com.greak.data.converters.FeedService;
import com.greak.data.database.UserInstance;
import com.greak.data.models.Account;
import com.greak.data.models.Post;

import java.util.ArrayList;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

class FeedPresenter extends EndlessListPresenter<Post> {

	FeedPresenter(ListViewLogic<Post> contract) {
		super(contract);
	}

	@Override
	public void downloadDataFromApi(int offset, boolean showLoadingView) {
		super.downloadDataFromApi(offset, showLoadingView);

		Account account = UserInstance.getInstance().getAccount();
		if (account != null) {
			handleDataDownload(account);
		} else {
			handleOnFailure(new NullPointerException("Account is null"));
		}
	}

	private void handleDataDownload(Account account) {
		AccountName accountName = new AccountName(account.getUsername());
		FeedService feedService = new FeedService(accountName, 0, (short) 10);
		try {
			feedService.getData()
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
					.doOnNext(this::handleOnResponse)
					.onErrorReturn(throwable -> {
						handleOnFailure(throwable);
						return new ArrayList<>();
					})
					.subscribe();
		} catch (SteemResponseException | SteemCommunicationException e) {
			handleOnFailure(e);
		}
	}
}