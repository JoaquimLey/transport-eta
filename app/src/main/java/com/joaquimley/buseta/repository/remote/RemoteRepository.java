package com.joaquimley.buseta.repository.remote;

import android.os.Handler;

import com.joaquimley.buseta.repository.local.db.DatabaseMockUtils;
import com.joaquimley.buseta.repository.local.db.entity.BusEntity;
import com.joaquimley.buseta.repository.local.db.entity.BusStationEntity;
import com.joaquimley.buseta.repository.local.db.entity.StationEntity;

import java.util.List;

/**
 * Created by joaquimley on 22/05/2017.
 */

public class RemoteRepository {


	private static RemoteRepository sInstance;

	public static RemoteRepository getInstance() {
		if (sInstance == null) {
			sInstance = new RemoteRepository();
		}
		return sInstance;
	}

	public void getBus(long busId, RemoteCallback<BusEntity> listener) {
		getMockBus(busId, listener);
	}

	public void getAllBuses(RemoteCallback<List<BusEntity>> listener) {
		getMockBuses(listener);
	}

	public void getStation(long stationId, RemoteCallback<StationEntity> listener) {
		getMockStation(stationId, listener);
	}

	public void getStationList(List<Long> stationIds, RemoteCallback<List<StationEntity>> listener) {
		getMockStationList(stationIds, listener);
	}

	public void getAllStations(RemoteCallback<List<StationEntity>> listener) {
		getMockStations(listener);
	}

	public void getBusStations(long busId, final RemoteCallback<List<BusStationEntity>> listener) {
		getMockBusStations(busId, listener);
	}

	public void getStationBuses(long stationId, RemoteCallback<List<BusStationEntity>> remoteCallback) {
		getMockStationBuses(stationId, remoteCallback);
	}

	public void getAllBusStations(RemoteCallback<List<BusStationEntity>> listener) {
		getMockAllBusStations(listener);
	}

	/******************************************************
	 * MOCK METHODS
	 *****************************************************/

	private void getMockStation(final long stationId, final RemoteCallback<StationEntity> listener) {
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				listener.onSuccess(new StationEntity(stationId, "350", "Description " + System.currentTimeMillis(),
						new StationEntity.StationLocation(35555, 3555)));
			}
		}, 1000);
	}

	private void getMockStationList(final List<Long> stationIds, final RemoteCallback<List<StationEntity>> listener) {
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				listener.onSuccess(DatabaseMockUtils.generateTestStationsIds(stationIds));
			}
		}, 4000);
	}

	private void getMockStations(final RemoteCallback<List<StationEntity>> listener) {
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				listener.onSuccess(DatabaseMockUtils.generateTestStations());
			}
		}, 4000);
	}

	private void getMockBus(final long busId, final RemoteCallback<BusEntity> listener) {
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				listener.onSuccess(new BusEntity(busId, "Remote " + System.currentTimeMillis()));
			}
		}, 1000);
	}

	private void getMockBuses(final RemoteCallback<List<BusEntity>> listener) {
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				listener.onSuccess(DatabaseMockUtils.generateTestRemoteBuses());
			}
		}, 4000);
	}

	private void getMockBusStations(final long busId, final RemoteCallback<List<BusStationEntity>> listener) {
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				listener.onSuccess(DatabaseMockUtils.generateTestRemoteBusStations(busId));
			}
		}, 3500);
	}

	private void getMockStationBuses(final long stationId, final RemoteCallback<List<BusStationEntity>> listener) {
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				listener.onSuccess(DatabaseMockUtils.generateTestRemoteStationBuses(stationId));
			}
		}, 3500);
	}

	private void getMockAllBusStations(final RemoteCallback<List<BusStationEntity>> listener) {
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				listener.onSuccess(DatabaseMockUtils.generateTestBusStations());
			}
		}, 3500);
	}
}