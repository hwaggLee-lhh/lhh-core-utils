package com.lhh.connection.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * 连接信息
 * @author hwaggLee
 * @createDate 2016年11月30日
 */
public abstract class BaseJdbcConnect {
	protected Logger logger = Logger.getLogger(this.getClass());
	protected String logname = this.getClass().getSimpleName();
	protected Connection connection;
	protected String USERNAME = "root";// 数据库密码
	protected String PASSWORD = "123456";
	protected String DRIVER = "com.mysql.jdbc.Driver";// 驱动信息
	protected String URL = "jdbc:mysql://ip:3306/databasename?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";
	
	protected Connection getConnection() {
		try {
			logger.info("init connection info.url="+URL);
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	public void closeStatement(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally{
				logger.debug("close Statement.");
			}
		}
	}

	public void closeResultSett(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally{
				logger.debug("close ResultSet.");
			}
		}
	}

	public void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally{
				logger.debug("close connection.");
			}
		}
	}
	

	
	/**
	 * 读取数据库总所有的表名
	 * @param conn：数据库连接
	 */
	public List<String> getTableName(Connection conn){
		List<String> list = new ArrayList<String>();
		if(conn == null )return list;
		try {
			ResultSet rs = conn.getMetaData().getTables("", "", "", null);
			while (rs.next()) {
				list.add(rs.getString("TABLE_NAME"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	

	/**
	 * 数据类型转化JAVA
	 * @param sqlType：类型名称
	 * @return
	 */
	public static String toSqlToJava(String sqlType) {
		if( sqlType == null || sqlType.trim().length() == 0 ) return sqlType;
		sqlType = sqlType.toLowerCase();
		switch(sqlType){
			case "nvarchar":return "String";
			case "char":return "String";
			case "varchar":return "String";
			case "text":return "String";
			case "nchar":return "String";
			case "blob":return "byte[]";
			case "integer":return "Long";
			case "tinyint":return "Integer";
			case "smallint":return "Integer";
			case "mediumint":return "Integer";
			case "bit":return "Boolean";
			case "bigint":return "java.math.BigInteger";
			case "float":return "Fload";
			case "double":return "Double";
			case "decimal":return "java.math.BigDecimal";
			case "boolean":return "Boolean";
			case "id":return "Long";
			case "date":return "java.util.Date";
			case "datetime":return "java.util.Date";
			case "year":return "java.util.Date";
			case "time":return "java.sql.Time";
			case "timestamp":return "java.sql.Timestamp";
			case "numeric":return "java.math.BigDecimal";
			case "real":return "java.math.BigDecimal";
			case "money":return "Double";
			case "smallmoney":return "Double";
			case "image":return "byte[]";
			default:
				System.out.println("-----------------》转化失败：未发现的类型"+sqlType);
				break;
		}
		return sqlType;
	}
	
}
