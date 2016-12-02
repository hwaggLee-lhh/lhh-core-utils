package com.lhh.format.file.peoprites;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


public class PropertiesFactory {
	private static Map<String,Object> container = new HashMap<String,Object>();
	
	static{
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader == null) {
			classLoader = PropertiesFactory.class.getClassLoader();
			}
		 try {
			InputStream is = classLoader.getResourceAsStream("global.app.properties");
			PropertiesHelper ph = new PropertiesHelper(is);
			container.put("app", ph);
			
		 } catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public static PropertiesHelper getPropertiesHelper(String pFile){
		PropertiesHelper ph = (PropertiesHelper)container.get(pFile);
		return ph;
	}
}
