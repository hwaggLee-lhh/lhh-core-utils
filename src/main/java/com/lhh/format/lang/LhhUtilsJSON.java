package com.lhh.format.lang;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.JSONUtils;

/**
 * json数据操作
 * 
 * @author hwaggLee
 * @createDate 2016年12月2日
 */
public class LhhUtilsJSON {

	/**
	 * JSONObject对象转JavaBean
	 * 
	 * @param <T>
	 * @param JSONObject
	 * @param JavaBean的class
	 * @return 转换结果（异常情况下返回null）
	 */
	public static <T> T jsonToBean(JSONObject json, Class<T> cls) {
		T t = null;
		try {
			t = cls.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (t == null)
			return null;
		// 取出Bean里面的所有方法
		Method[] methods = cls.getMethods();
		for (int i = 0; i < methods.length; i++) {
			// 取出方法名
			String methodName = methods[i].getName();
			// 取出方法的类型
			Class[] clss = methods[i].getParameterTypes();
			if (clss.length != 1) {
				continue;
			}
			// 若是方法名不是以set开始的则退出本次循环，且方法名的长度要大于3个字符
			if (!methodName.startsWith("set") && methodName.length() > 3) {
				// if (methodName.indexOf("set") < 0) {
				continue;
			}
			// 类型
			String type = clss[0].getSimpleName();
			String key = methodName.substring(3, 4).toLowerCase()
					+ methodName.substring(4);
			// 如果map里有该key
			if (json.has(key) && json.get(key) != null) {
				setValue(type, json.get(key), methods[i], t);
			}
		}
		return t;
	}

	/**
	 * 给JavaBean的某个属性设值
	 * 
	 * @param type
	 * @param value
	 * @param method
	 * @param bean
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws ParseException
	 */
	private static void setValue(String type, Object value, Method method,
			Object bean) {
		if (value != null && !"".equals(value)
				&& !JSONNull.getInstance().equals(value)) {
			try {
				if ("String".equals(type)) {
					method.invoke(bean, new Object[] { value });
				} else if ("int".equals(type) || "Integer".equals(type)) {
					method.invoke(bean,
							new Object[] { new Integer("" + value) });
				} else if ("double".equals(type) || "Double".equals(type)) {
					method.invoke(bean, new Object[] { new Double("" + value) });
				} else if ("float".equals(type) || "Float".equals(type)) {
					method.invoke(bean, new Object[] { new Float("" + value) });
				} else if ("long".equals(type) || "Long".equals(type)) {
					method.invoke(bean, new Object[] { new Long("" + value) });
				} else if ("int".equals(type) || "Integer".equals(type)) {
					method.invoke(bean,
							new Object[] { new Integer("" + value) });
				} else if ("boolean".equals(type) || "Boolean".equals(type)) {
					method.invoke(bean,
							new Object[] { Boolean.valueOf("" + value) });
				} else if ("BigDecimal".equals(type)) {
					method.invoke(bean, new Object[] { new BigDecimal(""
							+ value) });
				} else if ("Date".equals(type)) {
					Class dateType = method.getParameterTypes()[0];
					if ("java.util.Date".equals(dateType.getName())) {
						java.util.Date date = null;
						if ("String".equals(value.getClass().getSimpleName())) {
							String time = String.valueOf(value);
							String format = null;
							if (time.indexOf(":") > 0) {
								if (time.indexOf(":") == time.lastIndexOf(":")) {
									format = "yyyy-MM-dd H:mm";
								} else {
									format = "yyyy-MM-dd H:mm:ss";
								}
							} else {
								format = "yyyy-MM-dd";
							}
							SimpleDateFormat sf = new SimpleDateFormat();
							sf.applyPattern(format);
							date = sf.parse(time);
						} else {
							date = (java.util.Date) value;
						}

						if (date != null) {
							method.invoke(bean, new Object[] { date });
						}
					} else if ("java.sql.Date".equals(dateType.getName())) {
						Date date = null;
						if ("String".equals(value.getClass().getSimpleName())) {
							String time = String.valueOf(value);
							String format = null;
							if (time.indexOf(":") > 0) {
								if (time.indexOf(":") == time.lastIndexOf(":")) {
									format = "yyyy-MM-dd H:mm";
								} else {
									format = "yyyy-MM-dd H:mm:ss";
								}
							} else {
								format = "yyyy-MM-dd";
							}
							SimpleDateFormat sf = new SimpleDateFormat();
							sf.applyPattern(format);
							date = new Date(sf.parse(time).getTime());
						} else {
							date = (Date) value;
						}

						if (date != null) {
							method.invoke(bean, new Object[] { date });
						}
					}
				} else if ("Timestamp".equals(type)) {
					Timestamp timestamp = null;
					if ("String".equals(value.getClass().getSimpleName())) {
						String time = String.valueOf(value);
						String format = null;
						if (time.indexOf(":") > 0) {
							if (time.indexOf(":") == time.lastIndexOf(":")) {
								format = "yyyy-MM-dd H:mm";
							} else {
								format = "yyyy-MM-dd H:mm:ss";
							}
						} else {
							format = "yyyy-MM-dd";
						}
						SimpleDateFormat sf = new SimpleDateFormat();
						sf.applyPattern(format);
						timestamp = new Timestamp(sf.parse(time).getTime());
					} else {
						timestamp = (Timestamp) value;
					}

					if (timestamp != null) {
						method.invoke(bean, new Object[] { timestamp });
					}
				} else if ("byte[]".equals(type)) {
					method.invoke(bean,
							new Object[] { new String("" + value).getBytes() });
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * 将Model转换成JSONObject
	 */
	@SuppressWarnings("unchecked")
	public static JSONObject coverModelToJSONObject(Object o) throws Exception {
		JSONObject json = new JSONObject();
		Class clazz = o.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field f = fields[i];
			json.put(f.getName(), invokeMethod(clazz, f.getName(), o));
		}
		return json;
	}

	/**
	 * 将list转换成JSONArray
	 */
	public static JSONArray coverModelToJSONArray(List list) throws Exception {
		JSONArray array = null;
		if (list.isEmpty()) {
			return array;
		}
		array = new JSONArray();
		for (Object o : list) {
			array.add(coverModelToJSONObject(o));
		}
		return array;
	}

	@SuppressWarnings("unchecked")
	private static Object invokeMethod(Class c, String fieldName, Object o) {
		String methodName = fieldName.substring(0, 1).toUpperCase()
				+ fieldName.substring(1);
		Method method = null;
		try {
			method = c.getMethod("get" + methodName);
			return method.invoke(o);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据key从JSONObject对象中取得对应值
	 * 
	 * @param json
	 * @param key
	 * @return
	 * @throws JSONException
	 */
	public static Object getString(JSONObject json, String key)
			throws JSONException {
		Object ob = json.get(key);
		if (ob == null)
			return null;
		return ob;
	}

	/**
	 * 构造JSON数组
	 * 
	 * @param collect
	 *            一维数据列表
	 * @return
	 */
	public static String assembleArray(Collection<String> collect) {
		if (collect == null) {
			return "[]";
		}
		StringBuffer buf = new StringBuffer();
		for (String value : collect) {
			buf.append(',' + JSONUtils.quote(value));
		}
		// 删除第一个逗号
		if (buf.length() != 0) {
			buf.deleteCharAt(0);
		}
		return '[' + buf.toString() + ']';
	}

	/**
	 * 构造JSON二维数组
	 * 
	 * @param collect
	 *            二维数据列表
	 * @return
	 */
	public static String assembleArray2Level(Collection<List<String>> collect) {
		if (collect == null) {
			return "[]";
		}
		StringBuffer buf = new StringBuffer();
		for (List<String> subList : collect) {
			buf.append(',' + assembleArray(subList));
		}
		// 删除第一个逗号
		if (buf.length() != 0) {
			buf.deleteCharAt(0);
		}
		return '[' + buf.toString() + ']';
	}

	/**
	 * 构造JSON数组
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static String assembleArrayEnum(String key, String value) {
		return '[' + JSONUtils.quote(key) + ',' + JSONUtils.quote(value) + ']';
	}

	/**
	 * json转化日期处理
	 * @author hwaggLee
	 * @createDate 2016年12月16日
	 */
	public class LhhJsonDateValueProcessor implements JsonValueProcessor {

		private String datePattern = "yyyy-MM-dd";

		public LhhJsonDateValueProcessor() {
			super();
		}

		public LhhJsonDateValueProcessor(String format) {
			super();
			this.datePattern = format;
		}

		public Object processArrayValue(Object value, JsonConfig jsonConfig) {
			return process(value);
		}

		public Object processObjectValue(String key, Object value,
				JsonConfig jsonConfig) {
			return process(value);
		}

		private Object process(Object value) {
			try {
				if (value instanceof Date) {
					SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
					return sdf.format((Date) value);
				}
				return value == null ? "" : value.toString();
			} catch (Exception e) {
				return "";
			}
		}

		public String getDatePattern() {
			return datePattern;
		}

		public void setDatePattern(String pDatePattern) {
			datePattern = pDatePattern;
		}
	}
}
