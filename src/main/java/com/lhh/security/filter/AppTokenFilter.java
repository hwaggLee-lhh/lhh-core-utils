package com.lhh.security.filter;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.lhh.connection.url.RequestUtils;
import com.lhh.security.encryption.PasswordUtils;

/**
 * app端token权限验证
 * @author zhaoerqi
 *
 */
public class AppTokenFilter implements Filter{
	
	private Set<String> urlSet = new HashSet<String>();
	
	private static final Long REQ_MAX_TIME = 30*60*1000L;//30分钟
	private static final Long TOKEN_MAX_TIME = 90*24*60*60*1000L;//30天
//	private static final Long TOKEN_MAX_TIME =30*60*1000L;//30分钟

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) req;
		HttpServletResponse httpResponse = (HttpServletResponse)res;
		String valurl = httpRequest.getRequestURI().replaceFirst(httpRequest.getContextPath(), "");
		//过PC端访问
		if(!RequestUtils.isMobile(httpRequest)){
			filterChain.doFilter(req, res);
			return;
		}
		//获取token相关参数
		String time = req.getParameter("time");
		String key = req.getParameter("key");
		String sign = req.getParameter("sign");
		
		Map<String,Object> ret = new HashMap<String,Object>();
		//如果有token参数
		if(StringUtils.isNotBlank(time) && StringUtils.isNotBlank(key) && StringUtils.isNotBlank(sign)){
			//System.out.println("----------------->"+time+":"+key+":"+sign+":"+valurl);
			Map<String,Object> map = validateToken(valurl,time,key,sign);
			if((Boolean)(map.get("success"))){
				//更新session(session过期的情况下)
				updateSession(httpRequest,(String)map.get("userId"));
			}else{
				return;
			}
		}else{//如果没有token参数
			//如果需要用户信息
			if(urlSet.contains(valurl)){
				
			}
		}
		filterChain.doFilter(req, res);
	}
	
	/**
	 * 更新session(session过期的情况下)
	 * @param session
	 */
	private void updateSession(HttpServletRequest httpRequest,String userId) {
		
	}

	//验证token
	private Map<String,Object> validateToken(String url,String time,String key,String sign){
		Map<String,Object> map = new HashMap<String,Object>();
		boolean success = true;
		map.put("success", success);
		return map;
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		/**
		 * 初始化需要过滤的url
		 */
	}

	@Override
	public void destroy() {
	}

}
