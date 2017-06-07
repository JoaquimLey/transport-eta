package com.joaquimley.buseta.repository.local.db.converter;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by joaquimley on 22/05/2017.
 */

public class DateConverter {

	@TypeConverter
	public static Date toDate(Long timestamp) {
		return timestamp == null ? null : new Date(timestamp);
	}

	@TypeConverter
	public static Long toTimestamp(Date date) {
		return date == null ? null : date.getTime();
	}
}