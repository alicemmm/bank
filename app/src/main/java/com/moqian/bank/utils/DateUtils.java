package com.moqian.bank.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.content.Context;

import com.moqian.bank.R;
import com.moqian.bank.resultActivity;

public class DateUtils {

	private static Calendar c = Calendar.getInstance(TimeZone
			.getTimeZone("GMT+8"));

	public static String getYear(long timeMillis) {
		c.setTimeInMillis(timeMillis);

		return String.valueOf(c.get(Calendar.YEAR));
	}

	public static String getYearOfLastTwoBit(long timeMillis) {
		String year = getYear(timeMillis);
		return year.substring(year.length() - 3, year.length());

	}

	public static String getMonth(long timeMillis) {
		c.setTimeInMillis(timeMillis);

		return String.valueOf(c.get(Calendar.MONTH) + 1);
	}

	public static String getDay(long timeMillis) {
		c.setTimeInMillis(timeMillis);

		return String.valueOf(Calendar.DAY_OF_MONTH);
	}

	public static String getHour(long timeMillis) {
		c.setTimeInMillis(timeMillis);

		return String.valueOf(Calendar.HOUR_OF_DAY);
	}

	public static String getMinute(long timeMillis) {
		c.setTimeInMillis(timeMillis);

		return String.valueOf(Calendar.MINUTE);
	}

	public static String getSecond(long timeMillis) {
		c.setTimeInMillis(timeMillis);

		return String.valueOf(Calendar.SECOND);
	}

	public String jdkTimeStr(long timeMillis) {
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
		c.setTimeInMillis(timeMillis);
		return c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-"
				+ c.get(Calendar.DAY_OF_MONTH) + " "
				+ c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE)
				+ ":" + c.get(Calendar.SECOND);
	}

	public static String generateMessage(long mills, Context context,
		String payAmount, String tailNumber, String amount) {
		String year = DateUtils.getFormatDate("yy",mills);
		String month = DateUtils.getFormatDate("MM",mills);
		String dayString = DateUtils.getFormatDate("dd",mills);
		String hour = DateUtils.getFormatDate("HH",mills);
		String minute = DateUtils.getFormatDate("mm",mills);
		String sec = DateUtils.getFormatDate("ss",mills);
		String content = context.getString(R.string.message_content);
		content = String.format(content, tailNumber, year, month, dayString,
				hour, minute, payAmount, amount);
		return content;

	}
	/**
	 *
	 * @param mills
	 * @return
	 */
	public static String getDate(long mills){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(mills);
		return format.format(date);
		
	}
	/**
	 * @param mills
	 * @return
	 */
	public static String getDate(String mills){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(Long.valueOf(mills));
		return format.format(date);
		
	}
	/**
	 * @param mills
	 * @return
	 */
	public static String getDateWithMinute(String mills){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = new Date(Long.valueOf(mills));
		return format.format(date);
		
	}
	
	/**
	 * @param mills
	 * @return
	 */
	public static String getFormatDate(String format ,String mills){
		SimpleDateFormat format1 = new SimpleDateFormat(format);
		Date date = new Date(Long.valueOf(mills));
		return format1.format(date);
		
	}
	
	/**
	 * @param mills
	 * @return
	 */
	public static String getFormatDate(String format ,long mills){
		SimpleDateFormat format1 = new SimpleDateFormat(format);
		Date date = new Date(Long.valueOf(mills));
		return format1.format(date);
		
	}
}
