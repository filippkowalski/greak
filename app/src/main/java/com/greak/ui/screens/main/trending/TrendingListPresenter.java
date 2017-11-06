package com.greak.ui.screens.main.trending;

import com.chrono.src.ui.list.ListViewLogic;
import com.chrono.src.ui.list.endless.EndlessListPresenter;
import com.greak.data.converters.TrendingService;
import com.greak.data.models.Post;

import java.util.ArrayList;

import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

class TrendingListPresenter extends EndlessListPresenter<Post> {

	TrendingListPresenter(ListViewLogic<Post> contract) {
		super(contract);
	}

	@Override
	public void downloadDataFromApi(int offset, boolean showLoadingView) {
		super.downloadDataFromApi(offset, showLoadingView);

		handleDataDownload();
	}

	private void handleDataDownload() {
		TrendingService trendingService = new TrendingService();
		try {
			trendingService.getData()
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