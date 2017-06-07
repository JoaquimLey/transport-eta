package com.joaquimley.buseta.repository.local.model;


import com.joaquimley.buseta.repository.local.db.entity.StationEntity;

/**
 * Created by joaquimley on 23/05/2017.
 */

public interface StationModel extends IdentifiableModel {
	String getName();

	String getDescription();

	StationEntity.StationLocation getLocation();
}
