package com.joaquimley.buseta;

import android.app.Application;
import android.content.Context;

/**
 * Created by joaquimley on 23/05/2017.
 */

public class App extends Application {

	private static App sInstance;

	@Override
	public void onCreate() {
		super.onCreate();
		sInstance = this;
	}

	public static App getInstance() {
		if (sInstance == null) {
			sInstance = new App();
		}
		return sInstance;
	}

	public static Context getContext() {
		return getInstance().getApplicationContext();
	}
}
