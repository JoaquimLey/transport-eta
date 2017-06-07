package com.joaquimley.buseta.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.joaquimley.buseta.App;
import com.joaquimley.buseta.repository.local.db.dao.StationDao;
import com.joaquimley.buseta.repository.local.db.entity.StationEntity;
import com.joaquimley.buseta.repository.remote.RemoteCallback;
import com.joaquimley.buseta.repository.remote.RemoteRepository;

import java.util.List;

/**
 * Created by joaquimley on 23/05/2017.
 */

public class StationRepository {

	private static StationRepository sInstance;

	private final RemoteRepository remoteRepository;
	private final StationDao stationDao;

	public static StationRepository getInstance(RemoteRepository remoteRepository, StationDao stationDao) {
		if (sInstance == null) {
			sInstance = new StationRepository(remoteRepository, stationDao);
		}
		return sInstance;
	}

	private StationRepository(RemoteRepository remoteRepository, StationDao stationDao) {
		this.remoteRepository = remoteRepository;
		this.stationDao = stationDao;
	}

	public LiveData<StationEntity> load(long stationId) {
		fetchStation(stationId, new RemoteStationListener() {
			@Override
			public void onRemoteStationReceived(StationEntity stationEntity) {
				insertTask(stationEntity);
			}

			@Override
			public void onRemoteStationsReceived(List<StationEntity> busEntities) {
				// No-op
			}
		});
		return stationDao.loadStation(stationId);
	}

	public void loadNew(final long stationId) {
		fetchStation(stationId, new RemoteStationListener() {
			@Override
			public void onRemoteStationReceived(final StationEntity stationEntity) {
//				deleteTask(stationEntity, new TaskListener() {
//					@Override
//					public void onTaskFinished() {
						insertTask(stationEntity);
//					}
//				});
			}

			@Override
			public void onRemoteStationsReceived(final List<StationEntity> stationEntities) {
				// No op
			}
		});
	}

	public LiveData<List<StationEntity>> loadByIds(List<Long> stationIds) {
		fetchStationsByIds(stationIds, new RemoteStationListener() {
			@Override
			public void onRemoteStationReceived(final StationEntity stationEntity) {
			}

			@Override
			public void onRemoteStationsReceived(final List<StationEntity> stationEntities) {
				insertAllTask(stationEntities);
			}
		});
		return stationDao.loadByIds(stationIds);
	}

	/**
	 * Returns an observable for ALL the buses table changes
	 */
	public LiveData<List<StationEntity>> loadAll() {
		fetchStationList(new RemoteStationListener() {
			@Override
			public void onRemoteStationReceived(StationEntity busEntity) {

			}

			@Override
			public void onRemoteStationsReceived(final List<StationEntity> stationEntities) {
				insertAllTask(stationEntities);
			}
		});
		return stationDao.loadAll();
	}

	public void loadAllNewData() {
		fetchStationList(new RemoteStationListener() {
			@Override
			public void onRemoteStationReceived(StationEntity busEntity) {

			}

			@Override
			public void onRemoteStationsReceived(final List<StationEntity> stationEntities) {
//				deleteAllTask(new TaskListener() {
//					@Override
//					public void onTaskFinished() {
						insertAllTask(stationEntities);
//					}
//				});
			}
		});
	}

	/**
	 * Makes a call to the webservice. Keep it private since the view/viewModel should be 100% abstracted
	 * from the data source implementation.
	 * Updates the local database and keeps it as the single source of truth.
	 */
	private void fetchStation(long stationId, @NonNull final RemoteStationListener listener) {
		remoteRepository.getStation(stationId, new RemoteCallback<StationEntity>() {
			@Override
			public void onSuccess(StationEntity response) {
				listener.onRemoteStationReceived(response);
			}

			@Override
			public void onUnauthorized() {
				// TODO: 25/05/2017 Report this
			}

			@Override
			public void onFailed(Throwable throwable) {
				// TODO: 25/05/2017 Report this
			}
		});
	}

	private void fetchStationsByIds(List<Long> stationIds, final RemoteStationListener listener) {
		remoteRepository.getStationList(stationIds, new RemoteCallback<List<StationEntity>>() {
			@Override
			public void onSuccess(List<StationEntity> response) {
				listener.onRemoteStationsReceived(response);
			}

			@Override
			public void onUnauthorized() {

			}

			@Override
			public void onFailed(Throwable throwable) {

			}
		});
	}


	/**
	 * Makes a call to the webservice. Keep it private since the view/viewModel should be 100% abstracted
	 * from the data source implementation.
	 * Updates the local database and keeps it as the single source of truth.
	 */
	private void fetchStationList(@NonNull final RemoteStationListener listener) {
		remoteRepository.getAllStations(new RemoteCallback<List<StationEntity>>() {
			@Override
			public void onSuccess(List<StationEntity> response) {
				listener.onRemoteStationsReceived(response);
			}

			@Override
			public void onUnauthorized() {
				// TODO: 25/05/2017 Report this
			}

			@Override
			public void onFailed(Throwable throwable) {
				// TODO: 25/05/2017 Report this
			}
		});
	}

	private void insertTask(final StationEntity stationEntity) {
		insertTask(stationEntity, null);
	}

	private void insertTask(final StationEntity stationEntity, @Nullable final TaskListener listener) {
		new AsyncTask<Context, Void, Void>() {
			@Override
			protected Void doInBackground(Context... params) {
				stationDao.insert(stationEntity);
				return null;
			}

			@Override
			protected void onPostExecute(Void aVoid) {
				super.onPostExecute(aVoid);
				if (listener != null) {
					listener.onTaskFinished();
				}
			}
		}.execute(App.getContext());
	}

	private void insertAllTask(final List<StationEntity> stationEntities) {
		insertAllTask(stationEntities, null);
	}

	private void insertAllTask(final List<StationEntity> stationEntities,
	                           @Nullable final TaskListener listener) {
		new AsyncTask<Context, Void, Void>() {
			@Override
			protected Void doInBackground(Context... params) {
				stationDao.insertAll(stationEntities);
				return null;
			}

			@Override
			protected void onPostExecute(Void aVoid) {
				super.onPostExecute(aVoid);
				if (listener != null) {
					listener.onTaskFinished();
				}
			}
		}.execute(App.getContext());
	}

	private void deleteTask(final StationEntity busEntity) {
		deleteTask(busEntity, null);
	}

	private void deleteTask(final StationEntity stationEntity, @Nullable final StationRepository.TaskListener listener) {
		new AsyncTask<Context, Void, Void>() {
			@Override
			protected Void doInBackground(Context... params) {
				stationDao.delete(stationEntity);
				return null;
			}

			@Override
			protected void onPostExecute(Void aVoid) {
				super.onPostExecute(aVoid);
				if (listener != null) {
					listener.onTaskFinished();
				}
			}
		}.execute(App.getContext());
	}

	private void deleteAllTask() {
		deleteAllTask(null);
	}

	private void deleteAllTask(@Nullable final TaskListener listener) {
		new AsyncTask<Context, Void, Void>() {
			@Override
			protected Void doInBackground(Context... params) {
				stationDao.deleteAll();
				return null;
			}

			@Override
			protected void onPostExecute(Void aVoid) {
				super.onPostExecute(aVoid);
				if (listener != null) {
					listener.onTaskFinished();
				}
			}
		}.execute(App.getContext());
	}

	interface TaskListener {
		void onTaskFinished();
	}

	interface RemoteStationListener {
		void onRemoteStationReceived(StationEntity busEntity);

		void onRemoteStationsReceived(List<StationEntity> busEntities);
	}
}