package com.lhh.format.lang;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * 各种数据类型的转换
 * @author hwaggLee
 * @createDate 2016年11月30日
 */
public class LhhUtilsObject {

	/**
	 * object转化成string，空不转化,去掉首尾空格
	 * @param ob
	 * @return
	 */
	public static String formatToStringTrim(Object ob){
		if(ob == null )return null;
		if( ob instanceof byte[]){
			return new String((byte[])ob);
		}
		return ob.toString().trim();
	}
	/**
	 * object转化成string，空不转化,去掉首尾空格
	 * @param ob
	 * @param charsetName：装换字符编码
	 * @return
	 */
	public static String formatToStringTrim(Object ob,String charsetName){
		if(ob == null )return null;
		if( ob instanceof byte[]){
			try {
				return new String((byte[])ob,charsetName);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return ob.toString().trim();
	}
	
	/**
	 * 转换日期
	 * @param ob
	 * @return
	 */
	public static Date formatToDate(Object ob){
		if(ob == null || StringUtils.isBlank(ob.toString()))return null;
		try {
			return (Date)ob;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 转换Integer
	 * @param ob
	 * @return
	 */
	public static Integer formatToInteger(Object ob){
		if(ob == null || StringUtils.isBlank(ob.toString()) || !StringUtils.isNumeric(ob.toString()))return null;
		try {
			return Integer.parseInt(ob.toString().trim());
		} catch (Exception e) {
			System.out.println(ob);
			e.printStackTrace();
		}
		return null;
	}
	

	public static Long formatToLong(Object ob){
		if(ob == null || StringUtils.isBlank(ob.toString()) || !StringUtils.isNumeric(ob.toString()))return null;
		try {
			return Long.parseLong(ob.toString().trim());
		} catch (Exception e) {
			System.out.println(ob);
			e.printStackTrace();
		}
		return null;
	}
	
	
}
