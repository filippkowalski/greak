package com.greak.ui.common;

import com.chrono.src.common.constants.StringConstants;

import java.util.concurrent.TimeUnit;

public class TimeUtils {

	public int parseReadTimeToMinutes(String time) {
		String[] timeSplitArray = time.split(StringConstants.COLON);
		int minutes = Integer.parseInt(timeSplitArray[1]);
		if (minutes < 1) {
			return 1;
		} else {
			return minutes;
		}
	}

	public String getTimeAsHHMMSS(long timeInMillis) {
		long hours = TimeUnit.MILLISECONDS.toHours(timeInMillis);
		long minutes = TimeUnit.MILLISECONDS.toMinutes(timeInMillis) % 60;
		long seconds = TimeUnit.MILLISECONDS.toSeconds(timeInMillis) % 60;

		return hours + StringConstants.COLON + minutes + StringConstants.COLON + seconds;
	}
}