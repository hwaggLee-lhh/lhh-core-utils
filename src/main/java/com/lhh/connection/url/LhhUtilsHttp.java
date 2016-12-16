package com.lhh.connection.url;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * http请求
 * @author hwaggLee
 * @createDate 2016年11月30日
 */
public class LhhUtilsHttp {
    public static String execute(String url) {
        HttpClient httpClient = new HttpClient();
        PostMethod getMethod = new PostMethod(url);
        getMethod.addRequestHeader("Connection", "close");
        try {
            httpClient.executeMethod(getMethod);
            byte[] responseBody = getMethod.getResponseBody();
            String responseStr = new String(responseBody,"utf-8");
            return responseStr;
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static JSONObject getRemoteData(String url) {
        String result = LhhUtilsHttp.execute(url);
        if(StringUtils.isNotBlank(result)) {
            try {
                return JSONObject.parseObject(result);
            } catch (Exception e) {
            }
        }
        return new JSONObject();
    }
    
    

    /**
     * 获取某个网页的内容
     * @param url  网页的地址
     * @param code 网页的编码，不传就代表UTF-8
     * @return 网页的内容
     * @throws IOException
     */
    public static String sendGetChartset(String url, String code) {
    	if(url == null ) return null;
    	if( code == null) code = "utf-8";
    	//log.info("------>获取某个网页的内容url:"+url);
        BufferedReader bis = null; 
        InputStream is = null; 
        InputStreamReader inputStreamReader = null;
        StringBuffer result = new StringBuffer(); 
        try { 
            URLConnection connection = new URL(url).openConnection(); 
            connection.setConnectTimeout(20000);
            connection.setReadTimeout(20000);
            connection.setUseCaches(false);
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11");
            is = connection.getInputStream(); 
            inputStreamReader = new InputStreamReader(is, code);
            bis = new BufferedReader(inputStreamReader); 
            String line = null; 
            while ((line = bis.readLine()) != null) { 
                result.append(line); 
            } 
        }catch(Exception e){
        	e.printStackTrace();
        	System.out.println(e.getMessage()+":"+url);
        } finally { 
            if (inputStreamReader != null) {
                try { 
                    inputStreamReader.close();
                } catch (IOException e) { 
                    e.printStackTrace(); 
                } 
            }
            if (bis != null) { 
                try { 
                    bis.close(); 
                } catch (IOException e) { 
                    e.printStackTrace(); 
                } 
            } 
            if (is != null) { 
                try { 
                    is.close(); 
               } catch (IOException e) { 
                    e.printStackTrace(); 
                } 
            } 
        } 
        return result.toString(); 
    }
 
    
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        StringBuffer result = new StringBuffer(); 
        BufferedReader in = null;
        try {
        	String urlNameString = url;
        	if( StringUtils.isNotBlank(param)){
        		urlNameString = url + "?" + param;
        	}
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            /*Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }*/
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
            	result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result.toString();
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuffer result = new StringBuffer(); 
        try {
        	System.out.println("POST请求:url="+url+";param="+param);
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(20000);  
            conn.setReadTimeout(300000);  
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result.toString();
    }


    public static String getURLEncoderString(String param){
    	if( param == null || !param.contains("="))return param;
    	StringBuilder sb = new StringBuilder();
    	String[] p = param.split("&");
    	for (String s : p) {
    		String[] s1 = s.split("=");
    		try {
				sb.append(s1[0]+"="+URLEncoder.encode(s1[1], "utf-8")+"&");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
    	return sb.toString().substring(0, sb.toString().length()-1);
    }
    
    
    public static void main(String[] args) {
    }
}
