package com.greak.ui.screens.user_profile;

import android.content.Context;

import com.chrono.src.ui.list.ListViewLogic;
import com.chrono.src.ui.list.endless.EndlessListPresenter;
import com.greak.data.converters.LoginService;
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
	private UserProfileView view;

	UserProfilePresenter(AccountName accountName, ListViewLogic<Post> contract) {
		super(contract);
		this.accountName = accountName;
	}

	public void setView(UserProfileView view) {
		this.view = view;
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

	void onUserLogin(Context context, String username, String password) {
		LoginService service = new LoginService(context, username, password);
		try {
			service.getData()
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
					.doOnNext(aBoolean -> view.onUserLoggedIn(true))
					.onErrorReturn(throwable -> {
						view.onUserLoggedIn(false);
						return false;
					})
					.subscribe();
		} catch (SteemResponseException | SteemCommunicationException e) {
			view.onUserLoggedIn(false);
		}
	}
}