package com.joaquimley.buseta.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;

import com.joaquimley.buseta.App;
import com.joaquimley.buseta.repository.local.db.dao.BusStationDao;
import com.joaquimley.buseta.repository.local.db.entity.BusStationEntity;
import com.joaquimley.buseta.repository.local.db.entity.StationEntity;
import com.joaquimley.buseta.repository.remote.RemoteCallback;
import com.joaquimley.buseta.repository.remote.RemoteRepository;
import com.joaquimley.buseta.ui.viewobject.BusStationViewObject;

import java.util.List;

/**
 * Created by joaquimley on 23/05/2017.
 */

public class BusStationRepository {

	private static BusStationRepository sInstance;

	private final RemoteRepository remoteRepository;
	private final BusStationDao busStationDao;

	public static BusStationRepository getInstance(RemoteRepository remoteRepository, BusStationDao busStationDao) {
		if (sInstance == null) {
			sInstance = new BusStationRepository(remoteRepository, busStationDao);
		}
		return sInstance;
	}

	private BusStationRepository(RemoteRepository remoteRepository, BusStationDao busStationDao) {
		this.remoteRepository = remoteRepository;
		this.busStationDao = busStationDao;
	}

	public LiveData<List<StationEntity>> loadBusStations(Long busId) {
		fetchBusStations(busId, new RemoteBusStationListener() {
			@Override
			public void onRemoteBusStationsReceived(List<BusStationEntity> busStationEntityList) {
				insertAllTask(busStationEntityList);
			}
		});
		return busStationDao.getBusStations(busId);
	}

	public void loadBusStationsNew(Long busId) {
		fetchBusStations(busId, new RemoteBusStationListener() {
			@Override
			public void onRemoteBusStationsReceived(final List<BusStationEntity> busStationEntityList) {
//				deleteAllTask(new BusStationRepository.TaskListener() {
//					@Override
//					public void onTaskFinished() {
						insertAllTask(busStationEntityList);
//					}
//				});
			}
		});
	}

	public void loadStationBusesNewData(Long stationId) {
		fetchStationBuses(stationId, new RemoteBusStationListener() {
			@Override
			public void onRemoteBusStationsReceived(final List<BusStationEntity> busStationEntityList) {
//				deleteAllTask(new BusStationRepository.TaskListener() {
//					@Override
//					public void onTaskFinished() {
						insertAllTask(busStationEntityList);
//					}
//				});
			}
		});
	}

	/**
	 * Returns an observable for ALL the bus_station table changes
	 */
	public LiveData<List<BusStationEntity>> loadAllBusStationList() {
		fetchAllBusStations(new RemoteBusStationListener() {
			@Override
			public void onRemoteBusStationsReceived(List<BusStationEntity> busStationEntityList) {
				insertAllTask(busStationEntityList);
			}
		});
		return busStationDao.getAll();
	}

	public void loadAllBusStationListNewData() {
		fetchAllBusStations(new RemoteBusStationListener() {
			@Override
			public void onRemoteBusStationsReceived(final List<BusStationEntity> busStationEntityList) {
//				deleteAllTask(new TaskListener() {
//					@Override
//					public void onTaskFinished() {
						insertAllTask(busStationEntityList);
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
	private void fetchBusStations(long busId, final RemoteBusStationListener remoteBusStationListener) {
		remoteRepository.getBusStations(busId, new RemoteCallback<List<BusStationEntity>>() {
			@Override
			public void onSuccess(List<BusStationEntity> response) {
				remoteBusStationListener.onRemoteBusStationsReceived(response);
			}

			@Override
			public void onUnauthorized() {

			}

			@Override
			public void onFailed(Throwable throwable) {

			}
		});
	}

	private void fetchStationBuses(long stationId, final RemoteBusStationListener remoteBusStationListener) {
		remoteRepository.getStationBuses(stationId, new RemoteCallback<List<BusStationEntity>>() {
			@Override
			public void onSuccess(List<BusStationEntity> response) {
				remoteBusStationListener.onRemoteBusStationsReceived(response);
			}

			@Override
			public void onUnauthorized() {

			}

			@Override
			public void onFailed(Throwable throwable) {

			}
		});
	}

	private void fetchAllBusStations(final RemoteBusStationListener remoteBusStationListener) {
		remoteRepository.getAllBusStations(new RemoteCallback<List<BusStationEntity>>() {
			@Override
			public void onSuccess(List<BusStationEntity> response) {
				remoteBusStationListener.onRemoteBusStationsReceived(response);
			}

			@Override
			public void onUnauthorized() {

			}

			@Override
			public void onFailed(Throwable throwable) {

			}
		});
	}

	private void insertTask(final BusStationEntity busEntity) {
		insertTask(busEntity, null);
	}

	private void insertTask(final BusStationEntity busStationEntity, @Nullable final TaskListener listener) {
		new AsyncTask<Context, Void, Void>() {
			@Override
			protected Void doInBackground(Context... params) {
				busStationDao.insert(busStationEntity);
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

	private void insertAllTask(final List<BusStationEntity> busEntity) {
		insertAllTask(busEntity, null);
	}

	private void insertAllTask(final List<BusStationEntity> busStationEntities,
	                           @Nullable final TaskListener listener) {
		new AsyncTask<Context, Void, Void>() {
			@Override
			protected Void doInBackground(Context... params) {
				busStationDao.insertAll(busStationEntities);
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
				busStationDao.deleteAll();
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

	public LiveData<List<BusStationViewObject>> loadBusStationsEta(Long busId) {
		return busStationDao.getBusStationsEta(busId);
	}

	interface TaskListener {
		void onTaskFinished();
	}

	interface RemoteBusStationListener {
		void onRemoteBusStationsReceived(List<BusStationEntity> busStationEntityList);
	}
}