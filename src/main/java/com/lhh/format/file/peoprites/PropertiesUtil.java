package com.lhh.format.file.peoprites;

public class PropertiesUtil {
    static PropertiesHelper pHelper =null;
	static {
		pHelper = PropertiesFactory.getPropertiesHelper("app");
	}
	
	public static String getProperty(String key, String defaultValue) {
		return getpHelper().getValue(key,defaultValue);
	}
	
	public static String getProperty(String key) {
		return getpHelper().getValue(key);
	}

	private static PropertiesHelper getpHelper() {
		return pHelper;
	}
}
