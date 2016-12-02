/**
 * 
 */
package com.lhh.connection.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Title: 数据库操作SQL语句检验类 </p>
 * <p>Description: 提供标准的SQL语句检验方法 </p>
 * @date 2010-12-12     */
public class SQLValidater {
	
	private static  final Logger log = LoggerFactory.getLogger(SQLValidater.class);
	/** 1. 检验SQL是否为Null 或 length is 0 */
	public static boolean isNullSQL(String sql){
		boolean flag = true;
		
		if (sql == null || (sql = sql.trim()).length()==0 || !sql.contains(" ")) {
			flag = false;
		}
		
		return flag;
	}
	
	/** 2. 检验SQL是否为标准的数据库<插入>语句 */
	public static boolean checkInsertSQL(String sql){
		boolean flag = true;
		
		sql = sql.toLowerCase();
		if(!sql.contains("insert into") || !sql.contains("values") || !sql.contains("(") || !sql.contains(")")){
			flag = false;
		}
		
		return flag;
	}
	
	/** 3. 检验SQL是否为标准的数据库<删除>语句 */
	public static boolean checkDelSQL(String sql){
		boolean flag = false;
		
		sql = sql.toLowerCase();
		if( (sql.contains("delete") && sql.contains("from")) || sql.contains("drop")){
			flag = true;
		}
		
		return flag;
	}
	
	/** 4. 检验SQL是否为标准的数据库<更新与修改>语句
	 * update set 或  alter */
	public static boolean checkUpdateSQL(String sql){
		boolean flag = false;
		
		sql = sql.toLowerCase();
		if( (sql.contains("update") && sql.contains("set")) || sql.contains("alter")){
			flag = true;
		}
		
		return flag;
	}
	
	/** 5. 检验SQL是否为标准的数据库<查询>SQL语句 */
	public static boolean checkQuerySQL(String sql){
		boolean flag = false;
		
		sql = sql.toLowerCase();
		if(sql.contains("select") && sql.contains("from")){
			flag = true;
		}
		if(!flag){
			log.error(" sql illegal! [{}]",sql );
		}
		return flag;
	}
	
	/** 6. 检验是否为标准的<单列查询>的SQL语句    */
	public static boolean check1ColQuerySQL(String sql){
		boolean flag = true;
		
		sql = sql.toLowerCase();
		flag = checkQuerySQL(sql);
		
		if(flag){
			int start = sql.indexOf("select");
			int end   = sql.indexOf("from");
			String cols = sql.substring(start, end);
			if(cols.contains(" * ") || cols.contains(",")){
				flag = false;
			}
		}
		return flag;
	}
	
	/** 7. 检验是否使用聚合函数Count(*),并只返回1个数据的SQL语句     */
	public static boolean checkUsedCountSQL(String sql){
		boolean flag = true;
		
		sql = sql.toLowerCase();
		flag = checkQuerySQL(sql);
		if(flag){
			int start = sql.indexOf("select");
			int end   = sql.indexOf("from");
			String cols = sql.substring(start, end);
			if(!cols.contains("count") || !cols.contains("(") || !cols.contains("*") || !cols.contains(")")){
				flag = false;
			}
		}
		return flag;
	}
	
	
}
