package com.joaquimley.buseta.repository.local.db.entity;

import android.arch.persistence.room.PrimaryKey;

import com.joaquimley.buseta.repository.local.model.IdentifiableModel;

/**
 * Just an abstract class that makes extenders to return an id.
 * <p>
 * Created by joaquimley on 23/05/2017.
 */
abstract class IdentifiableEntity implements IdentifiableModel {

	@PrimaryKey
	protected long id;

	@Override
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
