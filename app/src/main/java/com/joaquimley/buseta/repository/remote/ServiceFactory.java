package com.joaquimley.buseta.repository.remote;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by joaquimley on 22/05/2017.
 */

public class ServiceFactory {

	private static final String BASE_URL = "MOCKED_DATA_ATM";
	private static final int HTTP_READ_TIMEOUT = 10000;
	private static final int HTTP_CONNECT_TIMEOUT = 6000;

	public static Service makeService() {
		return makeService(makeOkHttpClient());
	}

	private static Service makeService(OkHttpClient okHttpClient) {
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(BASE_URL)
				.client(okHttpClient)
				.build();

		return retrofit.create(Service.class);
	}

	private static OkHttpClient makeOkHttpClient() {
		OkHttpClient.Builder httpClientBuilder = new OkHttpClient().newBuilder();
		httpClientBuilder.connectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS);
		httpClientBuilder.readTimeout(HTTP_READ_TIMEOUT, TimeUnit.SECONDS);
		return httpClientBuilder.build();
	}
}
