package com.lhh.connection.url;

import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * 读取ip
 * @author huage
 * 2016年11月1日
 */
public class LocalIpUtils {

	public final static boolean islocalip = LocalIpUtils.isIP("61.152.154.22") ;
	
	public static void main(String[] args) throws Exception {
		System.out.println("开始");
		boolean isTrue = isIP("192.168.0.33");
		System.out.println("结束"+isTrue);
	}
	
	/**
	 * 验证服务地址与指定ip是否相等
	 * @param ip
	 * @return
	 */
	public static boolean isIP(String ip){
		Enumeration<NetworkInterface> en = null;
		try {
			en = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e1) {
			e1.printStackTrace();
		}
		if( en == null )return false;
		while (en.hasMoreElements()) {
			NetworkInterface ni = en.nextElement();
			try {
				List<InterfaceAddress> list = ni.getInterfaceAddresses();
				Iterator<InterfaceAddress> it = list.iterator();
				while (it.hasNext()) {
					InterfaceAddress ia = it.next();
					if(null!=ia.getBroadcast()){
						if( ip.equalsIgnoreCase(ia.getAddress().getHostAddress())){
							return true;
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	

	

	/**
	 * 获取IP地址
	 * @param request
	 * @return
	 */
    public static String getRemoteHost(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
    }


}
