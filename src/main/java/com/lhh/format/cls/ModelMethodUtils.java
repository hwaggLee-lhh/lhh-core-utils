package com.lhh.format.cls;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
/**
 * 模型方法工具类
 * @author yejia
 *
 */
public class ModelMethodUtils {
    private static Map<String, MethodsGetAndSet>  cacheMethod = new HashMap<String, MethodsGetAndSet>();
    public static Map<String, Method> getGetMethod(Object model) {
    	if(model==null) return null;
    	String className = model.getClass().toString();
    	if(cacheMethod.containsKey(model)) {
    		return cacheMethod.get(className).get;
    	}
    	MethodsGetAndSet gas = loadMethodsGetAndSet(model);
    	return gas.get;
    }
    public static Map<String, Method> getSetMethod(Object model) {
    	if(model==null) return null;
    	String className = model.getClass().toString();
    	if(cacheMethod.containsKey(model)) {
    		return cacheMethod.get(className).set;
    	}
    	MethodsGetAndSet gas = loadMethodsGetAndSet(model);
    	return gas.set;
    }
    public static MethodsGetAndSet loadMethodsGetAndSet(Object model) {
    	if(model==null) return null;
    	String className = model.getClass().toString();
    	if(cacheMethod.containsKey(className)) {
    		return cacheMethod.get(className);
    	}
		Method[] methods = model.getClass().getMethods();
		MethodsGetAndSet gas = new MethodsGetAndSet();
		for (Method method : methods) {
			/*BaseModel中不需要getClass*/
            if (method.getName().equals("getClass")) {
                continue;
            }
            Class returnType = method.getReturnType();
            /*不需要转换的类型*/
            if (returnType == byte[].class ||
                returnType == Logger.class) {
                continue;
            }
            String propertyName = ModelUtils.getPropertyName(method.getName());

            if (ModelUtils.isGetMethod(method)) {
            	gas.get.put(propertyName, method);
            } else if(ModelUtils.isSetMethod(method)) {
            	gas.set.put(propertyName, method);
            }
		}
		return gas;
    }
}
class MethodsGetAndSet {
	Map<String, Method> get = new HashMap<String, Method>();
	Map<String, Method> set = new HashMap<String, Method>();
}
