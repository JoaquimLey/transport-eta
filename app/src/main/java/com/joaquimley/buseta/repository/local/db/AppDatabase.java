package com.joaquimley.buseta.repository.local.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.joaquimley.buseta.repository.local.db.converter.DateConverter;
import com.joaquimley.buseta.repository.local.db.dao.BusDao;
import com.joaquimley.buseta.repository.local.db.dao.BusStationDao;
import com.joaquimley.buseta.repository.local.db.dao.CommentDao;
import com.joaquimley.buseta.repository.local.db.dao.StationDao;
import com.joaquimley.buseta.repository.local.db.entity.BusEntity;
import com.joaquimley.buseta.repository.local.db.entity.BusStationEntity;
import com.joaquimley.buseta.repository.local.db.entity.CommentEntity;
import com.joaquimley.buseta.repository.local.db.entity.StationEntity;

/**
 * Created by joaquimley on 22/05/2017.
 */

@Database(entities = {BusEntity.class, BusStationEntity.class,
		CommentEntity.class, StationEntity.class}, version = 1)
@TypeConverters(value = {DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {

	public static final String DATABASE_NAME = "bus-eta-db";

	private static AppDatabase sInstance;

	public abstract BusDao busDao();

	public abstract BusStationDao busStationDao();

	public abstract CommentDao commentDao();

	public abstract StationDao stationDao();
}