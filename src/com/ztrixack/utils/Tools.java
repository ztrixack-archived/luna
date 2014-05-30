package com.ztrixack.utils;

/**
 * Tools/Utility
 * 
 * @author Tanawat Hongthai - http://www.sic.co.th/
 * 
 * 		Copyright (C) 2013 Silicon Craft Technology Co.,Ltd. Licensed under the GNU Lesser
 * 		General Public License (LGPL) http://www.gnu.org/licenses/lgpl.html
 */

import android.annotation.SuppressLint;
import android.text.format.Time;
import android.util.Log;

@SuppressLint("DefaultLocale")
public class Tools {

	/**
	 * Check sum from byte array. last byte is empty.
	 * 
	 * @param data_byte
	 * 
	 * @return CRC
	 */
	public static byte checkSum(byte[] input) {
		byte sum = 0x00;
		for (int i = 0; i < input.length - 1; i++) {
			sum ^= input[i];
		}
		return sum;
	}

	/**
	 * Check sum from custom byte array.
	 * 
	 * @param data_byte
	 * @param index_start
	 * @param length
	 * 
	 * @return CRC
	 * @exception 0x00
	 */
	public static byte checkSum(byte[] data, int start, int length) {
		byte crc = 0x00;
		try {
			Preconditions.checkNotNull(data);
			Preconditions.checkPositionIndexes(start, start + length - 1,
					data.length);
			for (int i = start; i < start + length - 1; ++i) {
				crc ^= data[i];
			}
		} catch (Exception e) {
			Log.e("STM32Commands$xorCheckSum", e.getMessage());
			return 0x00;
		}
		return crc;
	}

	/**
	 * Get current date and time
	 * 
	 * @return date and time string format.
	 */
	public static String getDateAndTimeString() {
		// date and time
		Time today = new Time(Time.getCurrentTimezone());
		today.setToNow();
		return ("   " + String.format("%02d", today.monthDay) + "/"
				+ String.format("%02d", today.month + 1) + "/"
				+ String.format("%04d", today.year) + "   " + "    " + today
					.format("%k:%M:%S")) + "    ";
	}

	/**
	 * Function to convert milliseconds time to Timer Format
	 * Hours:Minutes:Seconds
	 */
	public static String milliSecondsToTimer(long milliseconds) {
		String finalTimerString = "";
		String secondsString;

		// Convert total duration into time
		int hours = (int) (milliseconds / (1000 * 60 * 60));
		int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
		int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
		// Add hours if there
		if (hours > 0) {
			finalTimerString = hours + ":";
		}

		// Prepending 0 to seconds if it is one digit
		if (seconds < 10) {
			secondsString = "0" + seconds;
		} else {
			secondsString = "" + seconds;
		}

		finalTimerString = finalTimerString + minutes + ":" + secondsString;

		// return timer string
		return finalTimerString;
	}

	/**
	 * Function to get Progress percentage
	 * 
	 * @param currentDuration
	 * @param totalDuration
	 */
	public static int getProgressPercentage(long currentDuration,
			long totalDuration) {
		Double percentage;

		long currentSeconds = (int) (currentDuration / 1000);
		long totalSeconds = (int) (totalDuration / 1000);

		// calculating percentage
		percentage = (((double) currentSeconds) / totalSeconds) * 100;

		// return percentage
		return percentage.intValue();
	}

	/**
	 * Function to change progress to timer
	 * 
	 * @param progress
	 * @param totalDuration
	 * @returns current duration in milliseconds
	 */
	public static int progressToTimer(int progress, int totalDuration) {
		int currentDuration;
		totalDuration = totalDuration / 1000;
		currentDuration = (int) ((((double) progress) / 100) * totalDuration);

		// return current duration in milliseconds
		return currentDuration * 1000;
	}
}
