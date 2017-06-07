package com.joaquimley.buseta.repository.local.db.entity;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;

import com.joaquimley.buseta.repository.local.model.StationModel;

/**
 * Created by joaquimley on 22/05/2017.
 */

@Entity(tableName = "stations")
public class StationEntity extends IdentifiableEntity implements StationModel {

	private String name;
	private String description;
	@Embedded
	private StationLocation location;

	public StationEntity() {
	}

	@Ignore
	public StationEntity(long id, String name, String description, StationLocation location) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.location = location;
	}

	@Ignore
	public StationEntity(StationModel stationModel) {
		this.id = stationModel.getId();
		this.name = stationModel.getName();
		this.description = stationModel.getDescription();
		this.location = stationModel.getLocation();
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public StationLocation getLocation() {
		return location;
	}

	public void setLocation(StationLocation location) {
		this.location = location;
	}

	public static class StationLocation {
		double latitude;
		double longitude;

		public StationLocation() {
		}

		@Ignore
		public StationLocation(double latitude, double longitude) {
			this.latitude = latitude;
			this.longitude = longitude;
		}

		public double getLatitude() {
			return latitude;
		}

		public void setLatitude(double latitude) {
			this.latitude = latitude;
		}

		public double getLongitude() {
			return longitude;
		}

		public void setLongitude(double longitude) {
			this.longitude = longitude;
		}
	}
}

