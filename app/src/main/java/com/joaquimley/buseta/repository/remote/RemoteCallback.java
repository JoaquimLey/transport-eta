package com.joaquimley.buseta.repository.remote;

import java.net.HttpURLConnection;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class RemoteCallback<T> implements Callback<T> {
	@Override
	public final void onResponse(Call<T> call, Response<T> response) {
		switch (response.code()) {
			case HttpsURLConnection.HTTP_OK:
			case HttpsURLConnection.HTTP_CREATED:
			case HttpsURLConnection.HTTP_ACCEPTED:
			case HttpsURLConnection.HTTP_NOT_AUTHORITATIVE:
				if (response.body() != null) {
					onSuccess(response.body());
				}
				break;

			case HttpURLConnection.HTTP_UNAUTHORIZED:
				onUnauthorized();
				break;

			default:
				onFailed(new Throwable("Default " + response.code() + " " + response.message()));
		}
	}

	@Override
	public final void onFailure(Call<T> call, Throwable t) {
		onFailed(t);
	}

	public abstract void onSuccess(T response);

	public abstract void onUnauthorized();

	public abstract void onFailed(Throwable throwable);
}

