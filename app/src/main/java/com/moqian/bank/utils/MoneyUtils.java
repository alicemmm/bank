package com.moqian.bank.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import android.R.integer;

import com.moqian.bank.resultActivity;

public class MoneyUtils {
	/**
	 * 格式化钱币数目x,xxx,xxx.xx
	 * @param str
	 * @return
	 */
	public static String formatMoney(String str) {
		 NumberFormat format = new DecimalFormat("¥,###.##");
		return format.format(Double.valueOf(str));
		
	}
	/**
	 * 格式化钱币数目x,xxx,xxx.xx没有¥字符在前面
	 * @param str
	 * @return
	 */
	public static String formatMoneyWithoutSymbol(String str) {
		if (str == null ||str.trim().length() == 0) {
			return str;
			
		}
		 NumberFormat format = new DecimalFormat(",###.##");
		return format.format(Double.valueOf(str));
		
	}
	
	/**
	 * 
	 * @param format x,xxx,xxx 到 xxxxxxx
	 * @return
	 */
	public static String getNoFormatMoney(String format) {
		if (format == null || format.trim().length() == 0) {
			return format;
			
		}
		String money = format.replace(",","");
		return  money;
		
	}
	
	/**
	 * 
	 * @给钱加上小数点,如1233,改为1233.00
	 * @return
	 */
	public static String getPointmoney(String  money) {
		if (money == null || money.trim().length() == 0) {
			return money;
			
		}
		money= money.replace(",","");
		StringBuilder builder = new StringBuilder(money);
		int pointIndex = builder.indexOf(".");
		if (pointIndex == -1) {
			
			builder = builder.append(".00");
			return builder.toString();
		}
		builder = builder.append("00");
		return builder.substring(0, pointIndex + 3);
		
	}


}
