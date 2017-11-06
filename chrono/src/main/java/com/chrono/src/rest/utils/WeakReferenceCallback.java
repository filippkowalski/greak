package com.chrono.src.rest.utils;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeakReferenceCallback<T> implements Callback<T> {

    private WeakReference<Callback<T>> callbackRef;

	public WeakReferenceCallback(Callback<T> callback) {
		this.callbackRef = new WeakReference<>(callback);
	}

	@Override
	public void onResponse(Call<T> call, Response<T> response) {
		Callback<T> callback = callbackRef.get();
		if (callback != null) {
			callback.onResponse(call, response);
		}
	}

	@Override
	public void onFailure(Call<T> call, Throwable t) {
		Callback<T> callback = callbackRef.get();
		if (callback != null) {
			callback.onFailure(call, t);
		}
	}
}