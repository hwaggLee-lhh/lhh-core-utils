package com.lhh.format.file.peoprites;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;


public class PropertiesHelper {
	private Properties objProperties;
	public PropertiesHelper(InputStream is) throws Exception {
		try{
			objProperties = new Properties();
			objProperties.load(is);	
		}
		catch(FileNotFoundException e){
            // e.printStackTrace();
			throw e;
		}
		catch(Exception e){
            // e.printStackTrace();
			throw e;
		}finally{
			is.close();
		}
	}
	
	public PropertiesHelper(InputStream is,String format) throws Exception {
		try{
			objProperties = new Properties();
			if(StringUtils.isNotBlank(format)&&format.equalsIgnoreCase("xml")){
				objProperties.loadFromXML(is);
			}
		}catch(FileNotFoundException e){
			throw e;
		}catch(Exception e){
			throw e;
		}finally{
			is.close();
		}
	}

    
	public void storefile(String pFileName){
		FileOutputStream outStream = null;
		try{
			File file = new File(pFileName + ".properties");
			outStream = new FileOutputStream(file);
			objProperties.store(outStream, "#eRedG4");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				outStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

    
	public String getValue(String key){
		return objProperties.getProperty(key);
	}

    
	public String getValue(String key, String defaultValue){
		return objProperties.getProperty(key, defaultValue);
	}

    
	public void removeProperty(String key){
		objProperties.remove(key);
	}
	
    
	public void setProperty(String key, String value){
		objProperties.setProperty(key, value);
	}
	
	public void printAllVlue(){
		 objProperties.list(System.out);
	}
}
