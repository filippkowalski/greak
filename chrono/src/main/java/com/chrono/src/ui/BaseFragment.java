package com.chrono.src.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chrono.src.Presenter;

import lombok.Getter;


public abstract class BaseFragment<P extends Presenter> extends Fragment {

    @Getter
    private P presenter;

    public abstract P createPresenter();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = createPresenter();
        presenter.onAttach();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (presenter != null) {
            presenter.onDetach();
        }
    }
}