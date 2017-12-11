package com.greak.ui.screens.main.votes;

import android.content.Context;

import com.chrono.src.ui.list.ListViewLogic;
import com.chrono.src.ui.list.endless.EndlessListPresenter;
import com.greak.data.converters.LoginService;
import com.greak.data.models.Post;

import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import lombok.Setter;

class VotesPresenter extends EndlessListPresenter<Post> {

	@Setter
	private VotesView view;

	VotesPresenter(ListViewLogic<Post> contract) {
		super(contract);
	}

	@Override
	public void downloadDataFromApi(int offset, boolean showLoadingView) {
		super.downloadDataFromApi(offset, showLoadingView);

//		call = RestService.getInstance()
//				.getApiService()
//				.getFavorites(offset, ConfigConstants.LIST_LOAD_LIMIT);
//		call.enqueue(getCallback());
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