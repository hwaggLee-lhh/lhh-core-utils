package com.lhh.format.lang;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.TreeMap;

import com.lhh.security.encryption.Md5Encrypt;

public class MapUtils {

	/**
	 * 功能：将一个Map按照Key字母升序构成一个QueryString. 并且加入时间混淆的hash串。
	 * 
	 * @param queryMap
	 *            query内容
	 * @param time
	 *            加密时候，为当前时间；解密时，为从querystring得到的时间；
	 * @param salt
	 *            加密salt
	 * @return
	 */
	public static String createHashedQueryString(Map<String, String> queryMap,
			long time, String salt) {

		Map<String, String> map = new TreeMap<String, String>(queryMap);
		String qs = createQueryString(map);
		if (qs == null) {
			return null;
		}
		time = time / 1000;

		String hash = Md5Encrypt.md5(String.format("%s&time=%d&salt=%s", qs,
				time, salt));
		hash = hash.toLowerCase();
		String htqs = String.format("%s&time=%d&hash=%s", qs, time, hash);

		return htqs;
	}

	/**
	 * 功能：用一个Map生成一个QueryString，参数的顺序不可预知。
	 * 
	 * @param queryString
	 * @return
	 */
	public static String createQueryString(Map<String, String> queryMap) {

		if (queryMap == null) {
			return null;
		}

		try {
			StringBuilder sb = new StringBuilder();
			for (Map.Entry<String, String> entry : queryMap.entrySet()) {
				if (entry.getValue() == null) {
					continue;
				}
				String key = entry.getKey().trim();
				String value = URLEncoder.encode(entry.getValue().trim(),
						"utf-8");
				sb.append(String.format("%s=%s&", key, value));
			}
			return sb.substring(0, sb.length() - 1);
		} catch (StringIndexOutOfBoundsException e) {
			e.printStackTrace();
			return null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

}
