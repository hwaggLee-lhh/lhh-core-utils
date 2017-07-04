package com.lhh.connection.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * 
 * 使用ThreadLocal来创建线程安全的连接singleton共享
 * @author hwaggLee
 *
 */
public class LhhThreadLocalJdbcConnect {

	//创建一个共享安全连接，其中保存connection，每次创建使用都分配一个一个初始的连接
    private static ThreadLocal<Connection> connectionHolder = new ThreadLocal<Connection>() {  
        /* 如果线程中没有对象，则初始化一个对象
         * (non-Javadoc)
         * @see java.lang.ThreadLocal#initialValue()
         */
        @Override  
        protected Connection initialValue() {  
            Connection conn = null;  
            try {  
                conn = DriverManager.getConnection(  
                        "jdbc:mysql://localhost:3306/test", "username",  
                        "password");  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
            return conn;  
        }  
    };  
  
    /**
     * 从线程中读取连接对象
     * @return
     */
    public static Connection getConnection() {  
        return connectionHolder.get();  
    }  
  
    public static void setConnection(Connection conn) {  
        connectionHolder.set(conn);  
    }  
}
