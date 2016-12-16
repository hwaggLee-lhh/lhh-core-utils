package com.lhh.format.lang;

import java.util.UUID;

/**
 * UUID编号
 * @author hwaggLee
 * @createDate 2016年11月30日
 */
public class LhhUtilsUUID {
	/**
	 * 
	 * @return String UUID
	 */
	public static String getUUID() {
		String s = UUID.randomUUID().toString();
		return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
	}

	/**
	 * @param number
	 *            int
	 * @return String[] UUID
	 */
	public static String[] getUUID(int number) {
		if (number < 1) {
			return null;
		}
		String[] ss = new String[number];
		for (int i = 0; i < number; i++) {
			ss[i] = getUUID();
		}
		return ss;
	}
	

	/**
	 * @param number
	 *            int
	 * @return String[] UUID
	 */
	public static String[] getUUIArrays(int number) {
		if (number < 1) {
			return null;
		}
		String[] ss = new String[number];
		for (int i = 0; i < number; i++) {
			ss[i] = getUUID();
		}
		return ss;
	}
}
