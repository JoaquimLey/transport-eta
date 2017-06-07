package com.joaquimley.buseta.repository.local.model;

import java.util.Date;

/**
 * Created by joaquimley on 23/05/2017.
 */

public interface BusStationModel extends IdentifiableModel {
	long getBusId();

	long getStationId();

	Date getScheduledTime();
}
