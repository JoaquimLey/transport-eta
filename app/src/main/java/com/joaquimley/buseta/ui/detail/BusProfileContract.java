package com.joaquimley.buseta.ui.detail;

import android.arch.lifecycle.LiveData;

import com.joaquimley.buseta.repository.local.db.entity.BusEntity;
import com.joaquimley.buseta.repository.local.db.entity.StationEntity;

import java.util.List;

/**
 * Created by joaquimley on 26/05/2017.
 */

interface BusProfileContract {

	interface ViewActions {

		void setBusId(long busId);

		void setStationId(long stationId);

		void fabButtonClicked();

		void onFavoriteButtonClicked();
	}

	interface DataStreams {
		LiveData<BusEntity> getBus();

		LiveData<List<StationEntity>> getBusStationList();
	}
}
