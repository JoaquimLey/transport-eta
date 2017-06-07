package com.joaquimley.buseta.repository.local.db;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.joaquimley.buseta.repository.local.db.entity.BusEntity;
import com.joaquimley.buseta.repository.local.db.entity.BusStationEntity;
import com.joaquimley.buseta.repository.local.db.entity.StationEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by joaquimley on 23/05/2017.
 */

public class DatabaseMockUtils {

	public static void populateMockDataAsync(final AppDatabase db) {
		PopulateDbAsync task = new PopulateDbAsync(db);
		task.execute();
	}

	public static void populateMockDataSync(final AppDatabase db) {
		populateWithTestData(db);
	}

	private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

		private final AppDatabase mDb;

		PopulateDbAsync(AppDatabase db) {
			mDb = db;
		}

		@Override
		protected Void doInBackground(final Void... params) {
			populateWithTestData(mDb);
			return null;
		}
	}

	private static void populateWithTestData(AppDatabase db) {
		db.beginTransaction();
		try {
			db.busDao().deleteAll();
			db.stationDao().deleteAll();
			db.busStationDao().deleteAll();
			db.commentDao().deleteAll();

			generateTestData(db);
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}

	@NonNull
	private static void generateTestData(AppDatabase db) {
		List<BusEntity> mockBusEntities = generateTestBuses();
		db.busDao().insertAll(mockBusEntities);

		List<StationEntity> mockStationEntities = generateTestStations();
		db.stationDao().insertAll(mockStationEntities);

		List<BusStationEntity> mockBusStationEntities = generateTestBusStations();
		db.busStationDao().insertAll(mockBusStationEntities);
	}

	@NonNull
	public static List<BusStationEntity> generateTestBusStations() {
		List<BusStationEntity> mockBusStationEntities = new ArrayList<>();
		mockBusStationEntities.add(new BusStationEntity(1, 1, 1, new Date(System.currentTimeMillis())));
		mockBusStationEntities.add(new BusStationEntity(2, 2, 2, new Date(System.currentTimeMillis())));
		mockBusStationEntities.add(new BusStationEntity(3, 3, 3, new Date(System.currentTimeMillis())));
		mockBusStationEntities.add(new BusStationEntity(4, 4, 4, new Date(System.currentTimeMillis())));
		mockBusStationEntities.add(new BusStationEntity(5, 5, 5, new Date(System.currentTimeMillis())));
		return mockBusStationEntities;
	}

	@NonNull
	public static List<StationEntity> generateTestStations() {
		List<StationEntity> mockStationEntities = new ArrayList<>();
		final StationEntity.StationLocation location = new StationEntity.StationLocation();

		location.setLatitude(1.2345d);
		location.setLongitude(1.2345d);
		mockStationEntities.add(new StationEntity(1, "Benfica", "Perto do cemiterio", location));

		location.setLatitude(2.2345d);
		location.setLongitude(2.2345d);
		mockStationEntities.add(new StationEntity(2, "Carnide", "Perto do metro", location));

		location.setLatitude(3.2345d);
		location.setLongitude(3.2345d);
		mockStationEntities.add(new StationEntity(3, "Ameixoeira", "Perto do Shopping", location));

		location.setLatitude(4.2345d);
		location.setLongitude(4.2345d);
		mockStationEntities.add(new StationEntity(4, "Pontinha", "Perto da PSP", location));

		location.setLatitude(5.2345d);
		location.setLongitude(5.2345d);
		mockStationEntities.add(new StationEntity(5, "S.Sebastiao", "Perto do El Corte Ingles", location));
		return mockStationEntities;
	}

	@NonNull
	public static List<StationEntity> generateTestStationsIds(List<Long> stationIds) {
		List<StationEntity> mockStationEntities = new ArrayList<>();
		final StationEntity.StationLocation location = new StationEntity.StationLocation();

		for (int i = 0; i < stationIds.size(); i++) {
			location.setLatitude(i + .2345d);
			location.setLongitude(i + .2345d);
			mockStationEntities.add(new StationEntity(stationIds.get(i), "Remote Station Name", "Hello world " + System.currentTimeMillis(),
					location));
		}
		return mockStationEntities;
	}

	@NonNull
	public static List<BusEntity> generateTestBuses() {
		List<BusEntity> mockBusEntities = new ArrayList<>();
		mockBusEntities.add(new BusEntity(1, "757"));
		mockBusEntities.add(new BusEntity(2, "758"));
		mockBusEntities.add(new BusEntity(3, "759"));
		mockBusEntities.add(new BusEntity(4, "760"));
		mockBusEntities.add(new BusEntity(5, "761"));
		mockBusEntities.add(new BusEntity(6, "762"));
		return mockBusEntities;
	}

	@NonNull
	public static List<BusEntity> generateTestRemoteBuses() {
		List<BusEntity> mockBusEntities = new ArrayList<>();
		mockBusEntities.add(new BusEntity(1, "757 " + System.currentTimeMillis()));
		mockBusEntities.add(new BusEntity(2, "758 " + System.currentTimeMillis()));
		mockBusEntities.add(new BusEntity(3, "759 " + System.currentTimeMillis()));
		mockBusEntities.add(new BusEntity(4, "760 " + System.currentTimeMillis()));
		mockBusEntities.add(new BusEntity(5, "761 " + System.currentTimeMillis()));
		mockBusEntities.add(new BusEntity(6, "762 " + System.currentTimeMillis()));
		return mockBusEntities;
	}

	@NonNull
	public static List<BusStationEntity> generateTestRemoteBusStations(long busId) {
		List<BusStationEntity> mockBusStationEntities = new ArrayList<>();
		mockBusStationEntities.add(new BusStationEntity(1, busId, 1, new Date(System.currentTimeMillis())));
		mockBusStationEntities.add(new BusStationEntity(2, busId, 2, new Date(System.currentTimeMillis())));
		mockBusStationEntities.add(new BusStationEntity(3, busId, 3, new Date(System.currentTimeMillis())));
		mockBusStationEntities.add(new BusStationEntity(4, busId, 4, new Date(System.currentTimeMillis())));
		mockBusStationEntities.add(new BusStationEntity(5, busId, 5, new Date(System.currentTimeMillis())));
		return mockBusStationEntities;
	}

	@NonNull
	public static List<BusStationEntity> generateTestRemoteStationBuses(long stationId) {
		List<BusStationEntity> mockBusStationEntities = new ArrayList<>();
		mockBusStationEntities.add(new BusStationEntity(6, 1, stationId, new Date(System.currentTimeMillis())));
		mockBusStationEntities.add(new BusStationEntity(7, 2, stationId, new Date(System.currentTimeMillis())));
		mockBusStationEntities.add(new BusStationEntity(8, 3, stationId, new Date(System.currentTimeMillis())));
		mockBusStationEntities.add(new BusStationEntity(9, 4, stationId, new Date(System.currentTimeMillis())));
		mockBusStationEntities.add(new BusStationEntity(10, 5, stationId, new Date(System.currentTimeMillis())));
		return mockBusStationEntities;
	}
}
