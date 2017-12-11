package com.greak.ui.screens.main.filtered_lists;

import com.chrono.src.ui.list.ListViewLogic;
import com.chrono.src.ui.list.endless.EndlessListPresenter;
import com.greak.data.converters.FilteredListService;
import com.greak.data.models.Post;
import com.greak.ui.screens.main.common.ListType;

import java.util.ArrayList;

import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

class FilteredListPresenter extends EndlessListPresenter<Post> {

	@ListType
	private int listType;

	FilteredListPresenter(int listType, ListViewLogic<Post> contract) {
		super(contract);
		this.listType = listType;
	}

	@Override
	public void downloadDataFromApi(int offset, boolean showLoadingView) {
		super.downloadDataFromApi(offset, showLoadingView);

		handleDataDownload();
	}

	private void handleDataDownload() {
		FilteredListService filteredListService = new FilteredListService(listType);
		try {
			filteredListService.getData()
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