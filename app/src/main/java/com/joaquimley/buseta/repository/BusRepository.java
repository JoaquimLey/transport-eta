package com.joaquimley.buseta.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.joaquimley.buseta.App;
import com.joaquimley.buseta.repository.local.db.dao.BusDao;
import com.joaquimley.buseta.repository.local.db.entity.BusEntity;
import com.joaquimley.buseta.repository.remote.RemoteCallback;
import com.joaquimley.buseta.repository.remote.RemoteRepository;

import java.util.List;

/**
 * Created by joaquimley on 23/05/2017.
 */

public class BusRepository {

	private static BusRepository sInstance;

	private final RemoteRepository remoteRepository;
	private final BusDao busDao;

	public static BusRepository getInstance(RemoteRepository remoteRepository, BusDao busDao) {
		if (sInstance == null) {
			sInstance = new BusRepository(remoteRepository, busDao);
		}
		return sInstance;
	}

	private BusRepository(RemoteRepository remoteRepository, BusDao busDao) {
		this.remoteRepository = remoteRepository;
		this.busDao = busDao;
	}

	public LiveData<BusEntity> load(long busId) {
		fetchBus(busId, new RemoteBusListener() {
			@Override
			public void onRemoteBusReceived(BusEntity busEntity) {
				insertTask(busEntity);
			}

			@Override
			public void onRemoteBusesReceived(List<BusEntity> busEntities) {
				// No-op
			}
		});
		return busDao.loadBus(busId);
	}

	public void loadNew(long busId) {
		fetchBus(busId, new RemoteBusListener() {
			@Override
			public void onRemoteBusReceived(final BusEntity busEntity) {
				insertTask(busEntity);
				// TODO: 07/06/2017 Remove all entities not in param busEntities
			}

			@Override
			public void onRemoteBusesReceived(final List<BusEntity> busEntities) {

			}
		});
	}

	/**
	 * Returns an observable for ALL the buses table changes
	 */
	public LiveData<List<BusEntity>> loadList() {
		fetchBusList(new RemoteBusListener() {
			@Override
			public void onRemoteBusReceived(BusEntity busEntity) {
				// No-op
			}

			@Override
			public void onRemoteBusesReceived(List<BusEntity> busEntities) {
				insertAllTask(busEntities);
			}
		});
		return busDao.loadAll();
	}

	public void loadListNew() {
		fetchBusList(new RemoteBusListener() {
			@Override
			public void onRemoteBusReceived(BusEntity busEntity) {
				// No-op
			}

			@Override
			public void onRemoteBusesReceived(final List<BusEntity> busEntities) {
				insertAllTask(busEntities);
				// TODO: 07/06/2017 Remove all entities not in param busEntities
			}
		});
	}

	/**
	 * Makes a call to the webservice. Keep it private since the view/viewModel should be 100% abstracted
	 * from the data sources implementation.
	 */
	private void fetchBus(long busId, @NonNull final RemoteBusListener listener) {
		remoteRepository.getBus(busId, new RemoteCallback<BusEntity>() {
			@Override
			public void onSuccess(BusEntity response) {
				listener.onRemoteBusReceived(response);
			}

			@Override
			public void onUnauthorized() {
				// TODO: 25/05/2017 Report this to the view
			}

			@Override
			public void onFailed(Throwable throwable) {
				// TODO: 25/05/2017 Report this to the view
			}
		});
	}


	/**
	 * Makes a call to the webservice. Keep it private since the view/viewModel should be 100% abstracted
	 * from the data sources implementation.
	 */
	private void fetchBusList(@NonNull final RemoteBusListener listener) {
		remoteRepository.getAllBuses(new RemoteCallback<List<BusEntity>>() {
			@Override
			public void onSuccess(List<BusEntity> response) {
				listener.onRemoteBusesReceived(response);
			}

			@Override
			public void onUnauthorized() {
				// TODO: 25/05/2017 Report this to the view
			}

			@Override
			public void onFailed(Throwable throwable) {
				// TODO: 25/05/2017 Report this to the view
			}
		});
	}

	private void insertTask(final BusEntity busEntity) {
		insertTask(busEntity, null);
	}

	private void insertTask(final BusEntity busEntity, @Nullable final TaskListener listener) {
		new AsyncTask<Context, Void, Void>() {
			@Override
			protected Void doInBackground(Context... params) {
				busDao.insert(busEntity);
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

	private void insertAllTask(final List<BusEntity> busEntity) {
		insertAllTask(busEntity, null);
	}

	private void insertAllTask(final List<BusEntity> busEntity,
	                           @Nullable final TaskListener listener) {
		new AsyncTask<Context, Void, Void>() {
			@Override
			protected Void doInBackground(Context... params) {
				busDao.insertAll(busEntity);
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

	private void deleteTask(final BusEntity busEntity) {
		deleteTask(busEntity, null);
	}

	private void deleteTask(final BusEntity busEntity, @Nullable final TaskListener listener) {
		new AsyncTask<Context, Void, Void>() {
			@Override
			protected Void doInBackground(Context... params) {
				busDao.delete(busEntity);
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
				busDao.deleteAll();
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

	interface RemoteBusListener {
		void onRemoteBusReceived(BusEntity busEntity);

		void onRemoteBusesReceived(List<BusEntity> busEntities);
	}
}