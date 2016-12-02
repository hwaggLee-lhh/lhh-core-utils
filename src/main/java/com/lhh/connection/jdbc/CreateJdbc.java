package com.lhh.connection.jdbc;


/**
 * 创建连接
 * @author hwaggLee
 * @createDate 2016年11月30日
 */
public class CreateJdbc extends BaseJdbcCube{
	
	public CreateJdbc() {
		super.USERNAME = "root";
		super.PASSWORD = "hp64123456";
		super.DRIVER = "com.mysql.jdbc.Driver";
		super.URL = "jdbc:mysql://61.152.154.49:3306/n3b?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";
		super.getConnection();
	}
	
	public CreateJdbc(String username, String password, String driver, String url) {
		super.USERNAME = username;
		super.PASSWORD = password;
		super.DRIVER = driver;
		super.URL = url;
		super.connection = super.getConnection();
	}
	
	
}
