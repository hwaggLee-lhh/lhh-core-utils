package com.lhh.format.file.peoprites;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


public class LhhPropertiesFactory {
	private static Map<String,Object> container = new HashMap<String,Object>();
	
	static{
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader == null) {
			classLoader = LhhPropertiesFactory.class.getClassLoader();
			}
		 try {
			InputStream is = classLoader.getResourceAsStream("global.app.properties");
			LhhPropertiesHelper ph = new LhhPropertiesHelper(is);
			container.put("app", ph);
			
		 } catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public static LhhPropertiesHelper getPropertiesHelper(String pFile){
		LhhPropertiesHelper ph = (LhhPropertiesHelper)container.get(pFile);
		return ph;
	}
}
