package com.lhh.format.lang;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


public class StrUtils {
    public static final Logger log = Logger.getLogger(StrUtils.class);
    /**","*/
    private static final String DIVIDER = ",";
    
    /**根据指定数字，返回此数字的文本，根据需要的长度，在返回的文本前加零
     * example:addZeroBefore(34, 4);返回"0034"
     * @param num
     * @param needLength
     * @return
     */
    public static String addZeroBefore(int num, int needLength) {
        StringBuffer buf = new StringBuffer("" + num);
        while (buf.length() < needLength) {
            buf.insert(0, "0");
        }
        return buf.toString();
    }
    
    /**按照分隔符“,”把字符串转换到列表
     * @param str 要转换的字符串
     * @return List
     */
    public static List<String> str2List(String str) {
        return str2List(str, DIVIDER);
    }
    
    /**按照制定分隔符把字符串转换到列表
     * @param str 要转换的字符串
     * @param token 分隔符
     * @return List
     */
    public static List<String> str2List(String str, String token) {
        return Arrays.asList(str.split(token));
    }
    
    /**按照分隔符“,”把列表转换到字符串
     * @param collect 要转换的列表
     * @return String
     */
    public static String list2Str(Collection<String> collect) {
        return list2Str(collect, DIVIDER);
    }
    
    /**按照制定分隔符把列表转换到字符串
     * @param collect 要转换的列表
     * @param token 分隔符
     * @return String
     */
    public static String list2Str(Collection<String> collect, String token) {
        if (StringUtils.isEmpty(token)) {
            throw new IllegalArgumentException("invalid token:" + token);
        }
        StringBuffer buf = new StringBuffer(collect.size() * 5);
        for (String string : collect) {
        	buf.append(token + string);
		}
        return buf.delete(0, token.length()).toString();
    }


	/**
	 * 首字母小写
	 * @param str
	 * @return
	 */
	public static String toLowerCaseFirstChar(String s) {
		if (Character.isLowerCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder())
					.append(Character.toLowerCase(s.charAt(0)))
					.append(s.substring(1)).toString();
	}

	/**
	 * 首字母大写
	 * @param str
	 * @return
	 */
	public static String toUpperCaseFirstChar(String s) {
		if (Character.isUpperCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder())
					.append(Character.toUpperCase(s.charAt(0)))
					.append(s.substring(1)).toString();
	}
}
