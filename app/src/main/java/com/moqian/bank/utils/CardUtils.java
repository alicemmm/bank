package com.moqian.bank.utils;

public class CardUtils {
	/**
	 *
	 * @return
	 */
	public static String getCardLastFourBits(String cardNumber) {
		cardNumber = cardNumber.replace(" ", "");
		String str;
		if (cardNumber.length() > 4) {
			str = cardNumber.substring(cardNumber.length() - 4, cardNumber.length());
		} else {
			str = cardNumber;
		}
		return str;
	}

}
