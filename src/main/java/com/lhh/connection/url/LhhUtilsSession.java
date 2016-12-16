package com.lhh.connection.url;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

/**
 * session存储
 * @author hwaggLee
 * @createDate 2016年12月2日
 */
public class LhhUtilsSession {

	public static final String userid="session_userid";
	public static final String username="session_username";
	public static final String loginUser="login_user";
	
	/**
	 * 存用户session
	 * @param session
	 * @param name
	 * @param value
	 */
	public static void addSession(HttpSession session,String name,Object value){
		session.setAttribute(name, value);
	}
	
	/**
	 * 存容器内容
	 * @param sc
	 * @param name
	 * @param value
	 */
	public static void addServletContext(ServletContext sc,String name,Object value){
		sc.setAttribute(name, value);
	}
	
	/**
	 * 读取当前session登陆用户的id
	 * @param session
	 * @return
	 */
	public static String getUserId(HttpSession session){
		Object ob = session.getAttribute(userid);
		if( ob == null ) return null;
		return ob.toString();
	}
	
}
