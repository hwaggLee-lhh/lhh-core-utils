package com.lhh.format.lang;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class BigDecimalUtils {
    public static final Logger log = Logger.getLogger(BigDecimalUtils.class);

    public static final BigDecimal ZERO = new BigDecimal("0");

    public static final BigDecimal ONE = new BigDecimal("1");

    /**
     * 两数相乘，四舍五入，保留2位小数
     * 
     * @param one
     * @param two
     * @return
     */
    public static BigDecimal multiply(BigDecimal one, BigDecimal two) {
        return multiply(one, two, 2);
    }

    /**
     * 两数相乘，四舍五入
     * 
     * @param one
     * @param two
     * @param scale
     *            保留位数
     * @return
     */
    public static BigDecimal multiply(BigDecimal one, BigDecimal two, int scale) {
        return one.multiply(two).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 三数相乘，四舍五入，保留2位小数
     * 
     * @param one
     * @param two
     * @return
     */
    public static BigDecimal multiply(BigDecimal one, BigDecimal two, BigDecimal three) {
        return one.multiply(two).multiply(three).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal divide(BigDecimal numerator, BigDecimal denominator) {
        return divide(numerator, denominator, 2);
    }

    public static BigDecimal divide(BigDecimal numerator, BigDecimal denominator, int scale) {
        return numerator.divide(denominator, scale, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal setScale(BigDecimal decimal) {
        return decimal.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal setScale(int scale, BigDecimal decimal) {
        return decimal.setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 解析String为BigDecimal，如果str为空返回0，如果抛出NumberFormatException，返回0
     * 
     * @param str
     *            需要解析的数字
     * @return BigDecimal
     */
    public static BigDecimal valueOf(String str) {
        return valueOf(str, ZERO);
    }

    /**
     * 解析String为BigDecimal，如果str为空返回0，如果抛出NumberFormatException，返回default
     * 
     * @param str
     *            需要解析的数字
     * @param defaultValue
     * @return BigDecimal
     */
    public static BigDecimal valueOf(String str, BigDecimal defaultValue) {
        if (StringUtils.isEmpty(str)) {
            return defaultValue;
        }
        try {
            return new BigDecimal(str);
        } catch (NumberFormatException ex) {
            return defaultValue;
        }
    }

    /**
     * 解析String，小数位控制
     * 
     * @param str
     * @param scale
     * @return
     */
    public static String valueOfScale(String str, int scale) {
        if (StringUtils.isBlank(str)) {
            return str;
        }
        try {
            BigDecimal propertyDecimal = new BigDecimal(str);
            propertyDecimal = BigDecimalUtils.setScale(scale, propertyDecimal);
            return propertyDecimal.toString();
        } catch (NumberFormatException ex) {
            return str;
        }
    }
    /**
     * 返回BigDecimal对象的string表示
     * 判断如果是jdk1.5或以上调用toPlainString方法，否则调用toString方法
     * 因程序有可能运行在jdk1.4版本中，所以用反射方式调用
     * @param bd
     * @return
     */
    @SuppressWarnings("all")
    public static String toString(BigDecimal bd) {
    	if(bd==null) return null;
    	String version = System.getProperty("java.version");
    	version = version.replaceAll("\\.", "");
    	if(version.length()>3) {
    		version = version.substring(0,3);
    	}
    	Integer ver = new Integer(version);
    	if(ver.intValue()<150) return bd.toString();
    	try {
    		Class c = Class.forName("java.math.BigDecimal");
    		Method m = c.getMethod("toPlainString", null);
    		Object o = m.invoke(bd, null);
    		return (String)o;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
    	return bd.toString();
    }
    
    public static String format(BigDecimal decimal, String pattern) {
        double dd = decimal.doubleValue();
        if (dd == 0) {
            return decimal.toString();
        }
        DecimalFormat format = new DecimalFormat(pattern);
        return format.format(dd).toString();
    }
    

	
	/**
	 * 比较是否相等
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean compareTo1Eq2(BigDecimal d1,BigDecimal d2){
		if( d1 == null ){
			if( d2 == null )return true;
			return false;
		}
		if( d2 == null )return false;
		return d1.compareTo(d2) == 0 ;
	}
	
	/**
	 * 比较d1<d2（其中为空返回false）
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean compareTo1Lt2(BigDecimal d1,BigDecimal d2){
		if( d1 == null )return false;
		if( d2 == null )return false;
		return d1.compareTo(d2) < 0 ;
	}

	/**
	 * 比较d1>d2（其中为空返回false）
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean compareTo1Gt2(BigDecimal d1,BigDecimal d2){
		if( d1 == null )return false;
		if( d2 == null )return false;
		return d1.compareTo(d2) > 0 ;
	}
	
	
	/**
	 * 转String，并四舍五入RoundingMode.HALF_UP,如果value=null则返回默认值
	 * @param value
	 * @param scale
	 * @param defaultValue
	 * @return
	 */
	public static String toStringScale(BigDecimal value,int scale,String defaultValue){
		if(value == null ) return defaultValue;
		return value.setScale(scale,RoundingMode.HALF_UP).toString();
	}
	

	/**
	 * 转String,如果value=null则返回默认值
	 * @param value
	 * @param scale
	 * @param defaultValue
	 * @param mode
	 * @return
	 */
	public static String toStringScale(BigDecimal value,int scale,String defaultValue,RoundingMode mode){
		if(value == null ) return defaultValue;
		return value.setScale(scale,mode).toString();
	}
	
	/**
	 * String转bigdecimal
	 * @param value
	 * @param scale
	 * @param defaultValue
	 * @return
	 */
	public static BigDecimal toBigDecimalScale(String value,int scale,BigDecimal defaultValue){
		if(StringUtils.isBlank(value))return defaultValue;
		try {
			return new BigDecimal(value).setScale(scale,RoundingMode.HALF_UP);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return defaultValue;
	}

	public static BigDecimal toBigDecimalScale(Object value,int scale,BigDecimal defaultValue){
		if(value == null )return defaultValue;
		return toBigDecimalScale(value.toString(), scale, defaultValue);
	}
	
	/**
	 * 乘积，并四舍五入RoundingMode.HALF_UP,如果value=null or two则返回默认值
	 * @param value
	 * @param scale
	 * @param defaultValue
	 * @return
	 */
	public static String multiplyScaleToStr(BigDecimal value, BigDecimal two,int scale,String defaultValue){
		if( value == null || two == null ) return defaultValue;
		return value.multiply(two).setScale(scale,RoundingMode.HALF_UP).toString();
	}
	
	

	/**
	 * 减法，并四舍五入RoundingMode.HALF_UP,如果value=null则返回默认值,denominator=为空则返回value的四舍五入值
	 * @param value
	 * @param denominator
	 * @param scale
	 * @param defaultValue
	 * @return
	 */
    public static String subtractScaleToStr(BigDecimal value, BigDecimal denominator,int scale,String defaultValue){
    	if( value == null )return defaultValue;
    	if( denominator == null || compareTo1Eq2(denominator, ZERO))return value.setScale(scale,RoundingMode.HALF_UP).toString();
        return value.subtract(denominator).setScale(scale,RoundingMode.HALF_UP).toString();
    }
    
    /**
     * 
	 * 减法，并四舍五入RoundingMode.HALF_UP,如果value=null则返回默认值,denominator=为空则返回value的四舍五入值
	 * 转换成BigDecimal时出现异常则返回defaultValue
     * @param value
     * @param denominator
     * @param scale
     * @param defaultValue：默认值
     * @return
     */
    public static String subtractScaleToStr(String value, String denominator,int scale,String defaultValue){
    	if( StringUtils.isBlank(value) )return defaultValue;
    	if( StringUtils.isBlank(denominator) )return value;
    	try {
			BigDecimal v = new BigDecimal(value);
			BigDecimal d = new BigDecimal(denominator);
			return v.subtract(d).setScale(scale,RoundingMode.HALF_UP).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return defaultValue;
    }

	public static BigDecimal toMillion(Long num,int scale){
		if(num==null )return BigDecimal.ZERO;
		return new BigDecimal(num).divide(new BigDecimal(10000), scale, BigDecimal.ROUND_HALF_UP);
	}
	public static BigDecimal toMillion(BigDecimal bigDecimal,int scale){
		if(bigDecimal==null )return BigDecimal.ZERO;
		return bigDecimal.divide(new BigDecimal(10000), scale, BigDecimal.ROUND_HALF_UP);
	}

	public static BigDecimal toBillion(BigDecimal bigDecimal,int scale){
		if(bigDecimal==null )return BigDecimal.ZERO;
		return bigDecimal.divide(new BigDecimal(100000000), scale, BigDecimal.ROUND_HALF_UP);
	}

	public static BigDecimal toBillion(Long num,int scale){
		if(num==null )return BigDecimal.ZERO;
		return new BigDecimal(num).divide(new BigDecimal(100000000), scale, BigDecimal.ROUND_HALF_UP);
	}

	public static BigDecimal setScale(BigDecimal num,int scale){
		if(num==null)return BigDecimal.ZERO;

		return num.setScale(scale,BigDecimal.ROUND_HALF_UP);
	}
}
