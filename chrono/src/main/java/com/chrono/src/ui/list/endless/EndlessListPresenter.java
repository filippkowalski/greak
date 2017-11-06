package com.chrono.src.ui.list.endless;

import com.chrono.src.Presenter;
import com.chrono.src.ui.list.DataDownloadLogic;
import com.chrono.src.ui.list.ListViewLogic;
import com.chrono.src.ui.states.error.ErrorTypeGenerator;

import java.util.List;

public abstract class EndlessListPresenter<D> implements Presenter, DataDownloadLogic {

	private ListViewLogic<D> contract;

	public EndlessListPresenter(ListViewLogic<D> contract) {
		this.contract = contract;
	}

	@Override
	public void onAttach() {
		downloadDataFromApi(0, true);
	}

	@Override
	public void downloadDataFromApi(int offset, boolean showLoadingView) {
		contract.showLoadingView(showLoadingView);
		contract.showErrorView(ErrorTypeGenerator.TYPE_UNSET, false);
	}

	protected void handleOnResponse(List<D> listResponse) {
		contract.showLoadingView(false);
		contract.showErrorView(ErrorTypeGenerator.TYPE_UNSET, false);

		if (listResponse.size() == 0) {
			contract.showErrorView(ErrorTypeGenerator.TYPE_NO_CONTENT, true);
		} else {
//			contract.onDataLoaded(listResponse, listResponse.getPrevious() == null);
			contract.onDataLoaded(listResponse, true); // TODO lazy loading
			contract.setHasLoadedAllData(false);
		}
	}

	protected void handleOnFailure(Throwable throwable) {
		throwable.printStackTrace();
		contract.showLoadingView(false);
		contract.showErrorView(ErrorTypeGenerator.TYPE_CONNECTION_PROBLEM, true);
	}

	@Override
	public void onDetach() {
		// no-op
	}
}