package com.lhh.connection.jdbc;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.lhh.format.cls.LhhUtilsModelMethod;
import com.lhh.format.cls.LhhUtilsModel;
import com.lhh.format.lang.LhhUtilsDateTime;

public class LhhUtilsSQLGenerate {
	public static String generateInsertSql(String[] columnIndex,String tableName, Object model) {
		StringBuffer sb = new StringBuffer("insert into ");
		sb.append(tableName);
		sb.append("(");
		Map<String, Method> methodByPro = LhhUtilsModelMethod.getGetMethod(model);
		StringBuffer columns = new StringBuffer();
		StringBuffer values = new StringBuffer();
		boolean hasData = false;
		for (int i = 0; i < columnIndex.length; i++) {
			Method method = methodByPro.get(columnIndex[i]);
			Object getObj = LhhUtilsModel.invokeGetMethod(model, method);
            if (getObj == null) continue;
            if(hasData) {
            	columns.append(",");
				values.append(",");
            } else {
            	hasData = true;
            }
            columns.append(columnIndex[i]);
			if(getObj instanceof String || 
					getObj instanceof Character) {
				values.append("'");
				values.append(getObj);
				values.append("'");
			} else if(getObj instanceof Date){
				values.append("'");
				values.append(LhhUtilsDateTime.date2StrDate((Date)getObj, LhhUtilsDateTime.FORMAT_yyyy_MM_dd_HH_mm_ss));
				values.append("'");
			} else {
				values.append(getObj);
			} 
		}
		sb.append(columns.toString());
		sb.append(") values (");
		sb.append(values.toString());
		sb.append(")");
		return sb.toString();
	}
	/**
	 * 
	 * @param columnIndex 表中各列
	 * @param tableName   表名
	 * @param model       获取各属性的源对象
	 * @param models      要插入的对象数组
	 * @return
	 */
	public static String generateInsertBatchSql(String[] columnIndex,String tableName, Object model, List<? extends Object> models) {
		StringBuffer sb = new StringBuffer("insert into ");
		StringBuffer columns = new StringBuffer();
		StringBuffer values = new StringBuffer();
		sb.append(tableName);
		sb.append("(");
		for(int i=0; i<columnIndex.length; i++){
			if(i>0) columns.append(",");
			columns.append(columnIndex[i]);
		}
		Map<String, Method> methodByPro = LhhUtilsModelMethod.getGetMethod(model);
		for(int i=0; i<models.size(); i++){
			if(i>0) values.append(",");
			values.append("(");
			for (int j = 0; j < columnIndex.length; j++) {
				Method method = methodByPro.get(columnIndex[j]);
				Object getObj = LhhUtilsModel.invokeGetMethod(models.get(i), method);
	            if (getObj == null) continue;
	            if(j>0) values.append(",");
				if(getObj instanceof String || 
						getObj instanceof Character) {
					values.append("'");
					values.append(getObj);
					values.append("'");
				} else if(getObj instanceof Date || getObj instanceof Timestamp){
					values.append("'");
					values.append(LhhUtilsDateTime.date2StrDate((Date)getObj, LhhUtilsDateTime.FORMAT_yyyy_MM_dd_HH_mm_ss));
					values.append("'");
				} else {
					values.append(getObj);
				}
			}
			values.append(")");
		}
		sb.append(columns.toString());
		sb.append(") values ");
		sb.append(values.toString());
		return sb.toString();
	}
    public static String generateUpdateSql(String[] columnIndex,String tableName, Object model, String primaryKey) {
    	StringBuffer sb = new StringBuffer("update ");
    	sb.append(tableName);
    	sb.append(" set ");
    	Map<String, Method> methodByPro = LhhUtilsModelMethod.getGetMethod(model);
		StringBuffer values = new StringBuffer();
		StringBuffer condition = new StringBuffer();
		boolean hasData = false;
    	for (int i = 0; i < columnIndex.length; i++) {
			Method method = methodByPro.get(columnIndex[i]);
			Object getObj = LhhUtilsModel.invokeGetMethod(model, method);
            if (getObj == null) continue;

            if(primaryKey.equals(columnIndex[i])) {
            	condition.append(primaryKey);
            	condition.append("=");
            	condition.append("'"+getObj+"'");
            	continue;
    		} else {
    			if(hasData) {
    				values.append(",");
                } else {
                	hasData = true;
                }
                values.append(columnIndex[i]);
                values.append("=");
                if(getObj instanceof String || 
                		getObj instanceof Character || 
    					getObj instanceof Date) {
    				values.append("'");
    				values.append(getObj);
    				values.append("'");
    			}  else {
    				values.append(getObj);
    			} 
    		}
            
    	}
    	sb.append(values.toString());
    	sb.append(" where ");
    	sb.append(condition.toString());
    	return sb.toString();
    }
    public static String generateSelectSql(String[] columnIndex,String tableName, Object model) {
    	StringBuffer sb = new StringBuffer("select * from ");
    	sb.append(tableName);
    	if(model==null) return sb.toString();
    	Map<String, Method> methodByPro = LhhUtilsModelMethod.getGetMethod(model);
    	StringBuffer condition = new StringBuffer();
		boolean hasData = false;
    	for (int i = 0; i < columnIndex.length; i++) {
			Method method = methodByPro.get(columnIndex[i]);
			Object getObj = LhhUtilsModel.invokeGetMethod(model, method);
            if (getObj == null) continue;

			if(hasData) {
				condition.append(" and ");
            } else {
            	hasData = true;
            }
			condition.append(columnIndex[i]);
			condition.append("=");
            if(getObj instanceof String) {
            	condition.append("'");
            	condition.append(getObj);
            	condition.append("'");
			}  else {
				condition.append(getObj);
			} 
    	}
    	if(StringUtils.isNotBlank(condition.toString())) sb.append(" where ");
    	sb.append(condition.toString());
    	return sb.toString();
    }
}
