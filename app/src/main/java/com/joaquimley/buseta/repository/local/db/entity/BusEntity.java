package com.joaquimley.buseta.repository.local.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;

import com.joaquimley.buseta.repository.local.model.BusModel;

/**
 * Created by joaquimley on 22/05/2017.
 */

@Entity(tableName = "buses")
public class BusEntity extends IdentifiableEntity implements BusModel {

	@ColumnInfo(name = "name")
	private String name;

	public BusEntity() {
	}

	@Ignore
	public BusEntity(long id, String name) {
		this.id = id;
		this.name = name;
	}

	@Ignore
	public BusEntity(BusModel bus) {
		this.id = bus.getId();
		this.name = bus.getName();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BusEntity)) return false;
		BusEntity that = (BusEntity) o;
		return this.id == that.id;
	}

	@Override
	public int hashCode() {
		return (int) (id ^ (id >>> 32));
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void update(BusEntity bus) {
		this.name = bus.getName();
	}
}
