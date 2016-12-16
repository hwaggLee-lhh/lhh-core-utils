package com.lhh.utils;

import org.apache.log4j.Logger;

/**
 * logger4j工具类
 * @author hwaggLee
 * @createDate 2016年12月15日
 */
public class LhhUtilsLogger4J {
	
	public static Logger getLogger(String loggerName){
		return Logger.getLogger(loggerName);
	}
	
	public static Logger getLogger(Class<?> cls){
		return Logger.getLogger(cls);
	}
	
}
