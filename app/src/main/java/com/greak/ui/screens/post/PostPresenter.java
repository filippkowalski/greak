package com.greak.ui.screens.post;

import android.content.Context;

import com.chrono.src.Presenter;
import com.greak.data.converters.LoginService;

import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PostPresenter implements Presenter {

	private PostView view;

	public PostPresenter() {
	}

	public void setView(PostView view) {
		this.view = view;
	}

	@Override
	public void onAttach() {
		// not used
	}

	@Override
	public void onDetach() {
		// not used
	}

	// TODO this and other similiar methods should be extracted
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
