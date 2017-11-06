package com.chrono.src.ui.list;

import com.chrono.src.ui.states.error.ErrorTypeGenerator;

import java.util.List;

public interface ListViewLogic<T> {

	void onDataLoaded(List<T> data, boolean firstRun);

	void setHasLoadedAllData(boolean loadedAllData);

	void showLoadingView(boolean show);

	void showErrorView(@ErrorTypeGenerator.ErrorType int errorType, boolean show);
}