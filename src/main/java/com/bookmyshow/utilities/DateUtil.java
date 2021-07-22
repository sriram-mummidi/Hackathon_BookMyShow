package com.bookmyshow.utilities;

import java.util.Date;

public class DateUtil {

	// This method returns current time, day, month and year
	public static String getTimeStamp() {
		Date date = new Date();
		return date.toString().replaceAll(":", "_").replaceAll(" ", "_");
	}

}
