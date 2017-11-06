package com.greak.ui.screens.user_profile;

import com.chrono.src.ui.list.ListViewLogic;
import com.chrono.src.ui.list.endless.EndlessListPresenter;
import com.greak.data.converters.UserDetailsService;
import com.greak.data.models.Post;

import java.util.ArrayList;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UserProfilePresenter extends EndlessListPresenter<Post> {

	private AccountName accountName;

	UserProfilePresenter(AccountName accountName, ListViewLogic<Post> contract) {
		super(contract);
		this.accountName = accountName;
	}

	@Override
	public void downloadDataFromApi(int offset, boolean showLoadingView) {
		super.downloadDataFromApi(offset, showLoadingView);

		UserDetailsService userDetailsService = new UserDetailsService(accountName, 100, (short) 100);
		try {
			userDetailsService.getData()
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