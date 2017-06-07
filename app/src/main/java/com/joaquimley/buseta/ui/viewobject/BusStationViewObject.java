package com.joaquimley.buseta.ui.viewobject;

import android.arch.persistence.room.ColumnInfo;

/**
 * TODO: 24/05/2017 implement on BusList
 * Use this to be used in the views (display object/view objec)
 */

public class BusStationViewObject {

	public long id;
	public String name;
	public String description;
	public String latitude;
	public String longitude;
	@ColumnInfo(name = "scheduled_time")
	public String eta;

}
