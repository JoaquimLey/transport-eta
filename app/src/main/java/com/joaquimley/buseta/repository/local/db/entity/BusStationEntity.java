package com.joaquimley.buseta.repository.local.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.joaquimley.buseta.repository.local.db.converter.DateConverter;
import com.joaquimley.buseta.repository.local.model.BusStationModel;

import java.util.Date;

/**
 * Created by joaquimley on 23/05/2017.
 */

@Entity(tableName = "bus_station",
		foreignKeys = {
				@ForeignKey(entity = BusEntity.class,
						parentColumns = "id",
						childColumns = "bus_id",
						onUpdate = ForeignKey.CASCADE,
						onDelete= ForeignKey.NO_ACTION),
//						onUpdate = ForeignKey.CASCADE
//						onDelete = ForeignKey.CASCADE),
				@ForeignKey(entity = StationEntity.class,
						parentColumns = "id",
						childColumns = "station_id",
						onUpdate = ForeignKey.CASCADE,
						onDelete= ForeignKey.NO_ACTION)},
//						onUpdate = ForeignKey.CASCADE
//						onDelete = ForeignKey.CASCADE)},
		indices = {@Index(value = {"bus_id", "station_id"}, unique = false)})
public class BusStationEntity {
	// Fields can be public or private with getters and setters.
	@PrimaryKey
	public long id;
	@ColumnInfo(name = "bus_id")
	public long busId;
	@ColumnInfo(name = "station_id")
	public long stationId;
	@TypeConverters({DateConverter.class})
	@ColumnInfo(name = "scheduled_time")
	public Date scheduledTime;

	public BusStationEntity() {
	}

	@Ignore
	public BusStationEntity(BusStationModel busStation) {
		this.busId = busStation.getBusId();
		this.stationId = busStation.getStationId();
		this.scheduledTime = busStation.getScheduledTime();
	}

	@Ignore
	public BusStationEntity(long id, long busId, long stationId, Date scheduledTime) {
		this.id = id;
		this.busId = busId;
		this.stationId = stationId;
		this.scheduledTime = scheduledTime;
	}
}