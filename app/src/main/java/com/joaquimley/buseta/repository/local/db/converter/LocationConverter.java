package com.joaquimley.buseta.repository.local.db.converter;

import android.arch.persistence.room.TypeConverter;
import android.location.Location;

/**
 * Created by joaquimley on 22/05/2017.
 *
 * @deprecated Using a station embedded Location model instead of the Framework's.
 */
@Deprecated
public class LocationConverter {

	@TypeConverter
	public static Location toLocation(String providerLatLongString) {
		String[] data = providerLatLongString.split(",");
		Location location = new Location(data[0]);
		location.setLatitude(Double.parseDouble(data[1]));
		location.setLongitude(Double.parseDouble(data[2]));
		return location;
	}

	@TypeConverter
	public static String toData(Location location) {
		return location.getProvider() + "," + location.getLatitude() + "," + location.getLongitude();
	}
}