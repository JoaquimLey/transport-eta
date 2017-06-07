package com.joaquimley.buseta.repository.local.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.joaquimley.buseta.repository.local.db.entity.StationEntity;

import java.util.List;

/**
 * Created by joaquimley on 22/05/2017.
 */

@Dao
public interface StationDao {

	@Query("SELECT * FROM stations where id = :stationId")
	LiveData<StationEntity> loadStation(long stationId);

	@Query("SELECT * FROM stations where id = :stationId")
	LiveData<StationEntity> loadById(long stationId);

	@Query("SELECT * FROM stations where id = :stationId")
	List<StationEntity> loadByIdSync(long stationId);

	@Query("SELECT * FROM stations where id IN (:stationIds)")
	LiveData<List<StationEntity>> loadByIds(List<Long> stationIds);

	@Query("SELECT * FROM stations")
	LiveData<List<StationEntity>> loadAll();

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void insertAll(List<StationEntity> stationEntityList);

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void insert(StationEntity... stationEntities);

	@Update
	void update(StationEntity... stationEntities);

	@Update
	void updateAll(List<StationEntity> stationEntities);

	@Delete
	void delete(StationEntity... stationEntities);

	@Delete
	void deleteAll(List<StationEntity> stationEntities);

	@Query("DELETE FROM stations")
	void deleteAll();
}
