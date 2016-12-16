package com.lhh.format.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

public abstract class LhhConfigPeopritesUpFile {
    private static Properties props = null;    
    private static File configFile = null; 
    private static long fileLastModified = 0L; 
    
    public abstract String getConfigFileName();
    
    public void init() { 
        URL url = LhhConfigPeopritesUpFile.class.getClassLoader().getResource(getConfigFileName()); 
        configFile = new File(url.getFile()); 
        fileLastModified = configFile.lastModified();      
        props = new Properties(); 
        load(); 
    } 
    
    public void load() { 
        try { 
            props.load(new InputStreamReader(new FileInputStream(configFile),"UTF-8")); 
            fileLastModified = configFile.lastModified(); 
        } catch (IOException e) {            
            throw new RuntimeException(e); 
        } 
    } 

    public String getConfig(String key) { 
        if ((configFile == null) || (props == null)) init(); 
        if (configFile.lastModified() > fileLastModified) load(); //当检测到文件被修改时重新加载配置文件
        return props.getProperty(key); 
    } 
    
    public static void main(String[] args){
    }
}
