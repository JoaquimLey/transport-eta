package com.joaquimley.buseta.repository.local.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import com.joaquimley.buseta.repository.local.db.entity.BusEntity;

import java.util.List;

/**
 * Created by joaquimley on 22/05/2017.
 */

@Dao
public interface BusDao {

	@Query("SELECT * FROM buses ")
	LiveData<List<BusEntity>> loadAll();

	@Query("SELECT * FROM buses where id = :busId LIMIT 1")
	LiveData<BusEntity> loadBus(long busId);

	@Query("SELECT * FROM buses where id = :busId")
	LiveData<List<BusEntity>> loadById(long busId);

	@Query("SELECT * FROM buses where id = :busId")
	List<BusEntity> getByIdSync(long busId);

	@Query("SELECT * FROM buses where id IN (:busIds)")
	LiveData<List<BusEntity>> loadByIds(long[] busIds);

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void insert(BusEntity... busEntities);

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void insertAll(List<BusEntity> busEntityList);

	@Update
	void update(BusEntity... buses);

	@Update
	void updateAll(List<BusEntity> busEntities);

	@Delete
	void delete(BusEntity... buses);

	@Delete
	void deleteAll(List<BusEntity> busEntities);

	@Query("DELETE FROM buses")
	void deleteAll();
}
