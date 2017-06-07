package com.joaquimley.buseta.repository.local.db;

/**
 * Created by joaquimley on 22/05/2017.
 */

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import com.joaquimley.buseta.App;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Creates the {@link AppDatabase} asynchronously, exposing a LiveData object to
 * notify of creation.
 */
public class DatabaseCreator {

	private static DatabaseCreator sInstance;

	private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

	private AppDatabase mDb;

	private final AtomicBoolean mInitializing = new AtomicBoolean(true);

	// For Singleton instantiation
	private static final Object LOCK = new Object();

	// FIXME: 25/05/2017 This is wrong, we're coupling the App object here.
	// FIXME: 25/05/2017 For the sake of simplicity.
	public synchronized static DatabaseCreator getInstance() {
		return getInstance(App.getInstance());
	}

	public synchronized static DatabaseCreator getInstance(Application application) {
		if (sInstance == null) {
			synchronized (LOCK) {
				if (sInstance == null) {
					sInstance = new DatabaseCreator(application);
				}
			}
		}
		return sInstance;
	}

	public DatabaseCreator(Application application) {
		createDb(application);
	}

	/**
	 * Used to observe when the database initialization is done
	 */
	public LiveData<Boolean> isDatabaseCreated() {
		return mIsDatabaseCreated;
	}

	@Nullable
	public AppDatabase getDatabase() {
		return mDb;
	}

	/**
	 * Creates or returns a previously-created database.
	 * <p>
	 * Although this uses an AsyncTask which currently uses a serial executor, it's thread-safe.
	 */
	private void createDb(Context context) {

		Log.d("DatabaseCreator", "Creating DB from " + Thread.currentThread().getName());

		if (!mInitializing.compareAndSet(true, false)) {
			return; // Already initializing
		}

		mIsDatabaseCreated.setValue(false);// Trigger an update to show a loading screen.
		new AsyncTask<Context, Void, AppDatabase>() {

			@Override
			protected AppDatabase doInBackground(Context... params) {
				Log.d("DatabaseCreator",
						"Starting bg job " + Thread.currentThread().getName());

				Context context = params[0].getApplicationContext();
				// Build the database!
				return Room.databaseBuilder(context.getApplicationContext(),
						AppDatabase.class, AppDatabase.DATABASE_NAME).build();
			}

			@Override
			protected void onPostExecute(AppDatabase appDatabase) {
				super.onPostExecute(appDatabase);
				mDb = appDatabase;
				// Now on the main thread, notify observers that the db is created and ready.
				mIsDatabaseCreated.setValue(true);
			}
		}.execute(context.getApplicationContext());
	}
}