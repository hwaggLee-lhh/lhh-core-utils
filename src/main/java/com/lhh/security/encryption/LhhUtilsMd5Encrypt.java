package com.lhh.security.encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.lhh.format.algorithm.LhhUtilsBinaryConvert;

/**
 * 功能：md5加密处理类
 * 版本：1.0
 * 日期：2010-12-21
 * 作者：feng
 **/
public class LhhUtilsMd5Encrypt {
	
	/**
	 * 功能：计算字符串的md5值
	 * 
	 * @param src
	 * @return
	 */
	public static String md5(String src) {			
		return digest(src, "MD5");			
	}
	
	/**
	 * 功能：根据指定的散列算法名，得到字符串的散列结果。
	 * 
	 * @param src
	 * @param name
	 * @return
	 */
	private static String digest(String src, String name){
		try {
			MessageDigest alg = MessageDigest.getInstance(name);
			byte[] result = alg.digest(src.getBytes());
			return LhhUtilsBinaryConvert.byte2hex(result);
		} catch (NoSuchAlgorithmException ex) {
			return null;
		}	
	}
}
