package com.lhh.format.file.peoprites;

public class LhhProperties {
    static LhhPropertiesHelper pHelper =null;
	static {
		pHelper = LhhPropertiesFactory.getPropertiesHelper("app");
	}
	
	public static String getProperty(String key, String defaultValue) {
		return getpHelper().getValue(key,defaultValue);
	}
	
	public static String getProperty(String key) {
		return getpHelper().getValue(key);
	}

	private static LhhPropertiesHelper getpHelper() {
		return pHelper;
	}
}
