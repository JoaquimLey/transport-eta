package com.joaquimley.buseta.repository.local.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.joaquimley.buseta.repository.local.db.entity.BusEntity;
import com.joaquimley.buseta.repository.local.db.entity.BusStationEntity;
import com.joaquimley.buseta.repository.local.db.entity.StationEntity;
import com.joaquimley.buseta.ui.viewobject.BusStationViewObject;

import java.util.List;

/**
 * Created by joaquimley on 22/05/2017.
 */

@Dao
public interface BusStationDao {

	@Query("SELECT * FROM bus_station"
	)
	LiveData<List<BusStationEntity>> getAll();

	@Query("SELECT * FROM bus_station"
	)
	List<BusStationEntity> getAllSync();

	@Query("SELECT * FROM stations " +
			"INNER JOIN bus_station ON bus_station.station_id == stations.id " +
			"WHERE bus_station.bus_id == :busId "
	)
	LiveData<List<StationEntity>> getBusStations(Long busId);

	@Query("SELECT s.*, b.scheduled_time FROM stations AS s " +
			"INNER JOIN bus_station AS b  ON b.station_id == s.id " +
			"WHERE b.bus_id == :busId"
	)
	LiveData<List<BusStationViewObject>> getBusStationsEta(Long busId);

	@Query("SELECT * FROM stations " +
			"INNER JOIN bus_station ON bus_station.station_id == stations.id " +
			"WHERE bus_station.bus_id == :busId "
	)
	List<StationEntity> getBusStationsSync(Long busId);

	@Query("SELECT * FROM buses " +
			"INNER JOIN bus_station ON bus_station.bus_id == buses.id " +
			"WHERE bus_station.station_id == :stationId "
	)
	LiveData<List<BusEntity>> getStationBuses(Long stationId);

	@Query("SELECT * FROM buses " +
			"INNER JOIN bus_station ON bus_station.bus_id == buses.id " +
			"WHERE bus_station.station_id == :stationId "
	)
	List<BusEntity> getStationBusesSync(Long stationId);

	@Query("SELECT * FROM bus_station " +
			"" +
			"WHERE bus_id == :busId "
	)
	LiveData<List<BusStationEntity>> findBusStationsByBusId(Long busId);

	@Query("SELECT * FROM bus_station " +
			"" +
			"WHERE bus_id == :busId "
	)
	List<BusStationEntity> findBusStationsByBusIdSync(Long busId);

	@Query("SELECT * FROM bus_station " +
			"" +
			"WHERE station_id == :stationId "
	)
	LiveData<List<BusStationEntity>> findBusStationsByStationId(Long stationId);

	@Query("SELECT * FROM bus_station " +
			"" +
			"WHERE station_id == :stationId "
	)
	List<BusStationEntity> findBusStationsByStationIdSync(Long stationId);

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void insert(BusStationEntity... bus_stationsEntities);

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void insertAll(List<BusStationEntity> busStationsEntityList);

	@Update
	void update(BusStationEntity... busStationsEntities);

	@Update
	void updateAll(List<BusStationEntity> busStationsEntities);

	@Delete
	void delete(BusStationEntity... busStationsEntities);

	@Delete
	void deleteAll(List<BusStationEntity> busStationsEntities);

	@Query("DELETE FROM bus_station")
	void deleteAll();
}
