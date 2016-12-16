package com.lhh.format.lang;


import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import junit.framework.Assert;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;

/**
 * 日期格式化
 * @author hwaggLee
 * @createDate 2016年11月30日
 */
public class LhhUtilsDateTime
{
    public static final Logger log = Logger.getLogger(LhhUtilsDateTime.class);

    public final static String FORMAT_yyyy_MM_dd = "yyyy-MM-dd";
    public final static String FORMAT_yyyy_M_d = "yyyy-M-d";
    public final static String FORMAT_yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public final static String FORMAT_yyyy_nian_MM_yue_mm_ri = "yyyy年MM月dd日";
    public final static String FORMAT_yyyy_nian_M_yue_m_ri = "yyyy年M月d日";
	public static final String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
	public static final String yyyy_MM_dd = "yyyy-MM-dd";
	public static final String yyyyMMdd = "yyyyMMdd";
	public static final String yyyyMM = "yyyyMM";
	
    
    public static void main(String[] args) {
        System.out.println(convertChar2ChineseChar("2008-10-20"));
        System.out.println(convertChar2ChineseChar("2008-11-21"));
        System.out.println(convertChar2ChineseChar("2008-01-29"));
    }
    
    /**日期显示中文
     * 例：
     * 2008-10-20->二〇〇八年十月二十日
     * 2008-11-21->二〇〇八年十一月二十一日
     * 2008-01-29->二〇〇八年一月二十九日
     * @param strDate yyyy-MM-dd
     * @return 中文显示日期
     */
    public static String convertChar2ChineseChar(String strDate) {
        if (StringUtils.isEmpty(strDate)) {
            return "";
        }
        String year = strDate.substring(0, 4);
        String month = strDate.substring(5, 7);
        String day = strDate.substring(8, 10);
        String ret = convertChar2ChineseOne(year) + "年"
            + convertChar2ChineseTwo(month) + "月"
            + convertChar2ChineseTwo(day) + "日";
        return ret;
    }
    
    public static String convertChar2ChineseTwo(String str) {
        int num = Integer.parseInt(str);
        Assert.assertTrue(num >= 0 && num <= 99);
        if (num < 10) {
            return convertChar2ChineseOne("" + num);
        }
        int tenNum = Integer.parseInt(str.substring(0, 1));//十位
        int singleNum = Integer.parseInt(str.substring(1));//个位
        String ten = "十";
        if (tenNum > 1) {
            ten = convertChar2ChineseOne("" + tenNum) + ten;
        }
        String single = "";
        if (singleNum > 0) {
            single = convertChar2ChineseOne("" + singleNum);
        }
        return ten + single;
    }
    
    private static String convertChar2ChineseOne(String str) {
        str = str.replace('0', '〇');
        str = str.replace('1', '一');
        str = str.replace('2', '二');
        str = str.replace('3', '三');
        str = str.replace('4', '四');
        str = str.replace('5', '五');
        str = str.replace('6', '六');
        str = str.replace('7', '七');
        str = str.replace('8', '八');
        str = str.replace('9', '九');
        return str;
    }
    
    /**
     * Date转换到Calendar
     * @param date 要转换的Date
     * @return Calendar
     */
    public static Calendar date2Calendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 设置指定的Calendar“时、分、妙”为零
     * @param calendar Calendar
     */
    public static void setTimeZero(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
    }

    /**
     * 得到当前时间的字符串yyyy-MM-dd
     * @return String
     */
    public static String now2StrDate() {
        return now2Str(FORMAT_yyyy_MM_dd);
    }

    /**
     * 得到当前时间的字符串yyyy-MM-dd HH:mm:ss
     * @return String
     */
    public static String now2StrDateTime() {
        return now2Str(FORMAT_yyyy_MM_dd_HH_mm_ss);
    }

    /**
     * 得到当前时间的字符串
     * @param format 字符串格式
     * @return String
     * @see org.apache.commons.lang.time.DateFormatUtils.DateFormatUtils#format(Date, String)
     */
    public static String now2Str(String format) {
        return DateFormatUtils.format(new Date(), format);
    }

    /**
     * Date转换到字符串yyyy-MM-dd
     * @param date Date
     * @return String yyyy-MM-dd
     * @see org.apache.commons.lang.time.DateFormatUtils.DateFormatUtils#format(Date, String)
     */
    public static String date2StrDate(Date date) {
        return DateFormatUtils.format(date, FORMAT_yyyy_MM_dd);
    }
    
    /**
     * Date转换到字符串
     * @param date
     * @param format
     * @return
     */
    public static String date2StrDate(Date date, String format) {
    	if (date==null) 
    		return null;
    	else
    		return DateFormatUtils.format(date, format);
    
    }

    /**
     * Date转换到字符串yyyy-MM-dd HH:mm:ss
     * @param date Date
     * @return String yyyy-MM-dd HH:mm:ss
     * @see org.apache.commons.lang.time.DateFormatUtils.DateFormatUtils#format(Date, String)
     */
    public static String date2StrDateTime(Date date) {
        return DateFormatUtils.format(date, FORMAT_yyyy_MM_dd_HH_mm_ss);
    }

    /**
     * Calendar转换到字符串yyyy-MM-dd
     * @param calendar Calendar
     * @return String yyyy-MM-dd
     * @see org.apache.commons.lang.time.DateFormatUtils.DateFormatUtils#format(Date, String)
     */
    public static String calendar2StrDate(Calendar calendar) {
        return date2StrDate(calendar.getTime());
    }

    /**
     * Calendar转换到字符串yyyy-MM-dd HH:mm:ss
     * @param calendar Calendar
     * @return String yyyy-MM-dd HH:mm:ss
     * @see org.apache.commons.lang.time.DateFormatUtils.DateFormatUtils#format(Date, String)
     */
    public static String calendar2StrDateTime(Calendar calendar) {
        return date2StrDateTime(calendar.getTime());
    }

    /**
     * 字符串yyyy-MM-dd转换到Calendar类型
     * @param dateStr yyyy-MM-dd
     * @return Calendar
     */
    public static Calendar strDate2Calendar(String dateStr) {
        return str2Calendar(dateStr, FORMAT_yyyy_MM_dd);
    }

    /**
     * 字符串yyyy-MM-dd转换到Date类型
     * @param dateStr yyyy-MM-dd
     * @return Date
     */
    public static Date strDate2Date(String dateStr) {
        return str2Date(dateStr, FORMAT_yyyy_MM_dd);
    }

    /**
     * 字符串yyyy-MM-dd HH:mm:ss转换到Calendar类型
     * @param dateStr yyyy-MM-dd HH:mm:ss
     * @return Calendar
     */
    public static Calendar strDateTime2Calendar(String dateStr) {
        return str2Calendar(dateStr, FORMAT_yyyy_MM_dd_HH_mm_ss);
    }

    /**
     * 字符串yyyy-MM-dd HH:mm:ss转换到Date类型
     * @param dateStr yyyy-MM-dd HH:mm:ss
     * @return Date
     */
    public static Date strDateTime2Date(String dateStr) {
        return str2Date(dateStr, FORMAT_yyyy_MM_dd_HH_mm_ss);
    }

    /**
     * 字符串转换到Date类型
     * @param dateStr 需要转换的字符串
     * @param format 转换格式
     * @return Date
     */
    public static Date str2Date(String dateStr, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setLenient(false);
        Date date = dateFormat.parse(dateStr, new ParsePosition(0));
        return date;
    }

    /**
     * 字符串转换到Calendar类型
     * @param dateStr 需要转换的字符串
     * @param format 转换格式
     * @return Calendar
     */
    public static Calendar str2Calendar(String dateStr, String format) {
        Calendar calendar = Calendar.getInstance();
        if(null != dateStr)
        {
        	calendar.setTime(str2Date(dateStr, format));
        }
        return calendar;
    }
    
    /** 
     *  得到当前日期的Calendar类型
     * @return Calendar;
     */
    public static Calendar now2Calendar() {
        return Calendar.getInstance();
    }
    
    /** 
     *  得到当前日期的下一天
     * @return Calendar;
     */   
    public static String getNextDay(String dateTime) {
        Calendar now = Calendar.getInstance();
        SimpleDateFormat simpledate = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpledate.parse(dateTime);
        } catch (ParseException ex) {
            System.out.println("日期格式不符合要求：" + ex.getMessage());
            return null;
        }
        now.setTime(date);
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH) + 1;
        now.set(year, month, day);
        String time = simpledate.format(now.getTime());
        return time;
    }
   
    /**
    * 计算指定日期的上一天
    * 
    * @param dateTime
    * @日期，格式为：yyyy-MM-dd
    * @return
    */
    public static String getBeforeDay(String dateTime) {
    	if( dateTime == null) return null;
       Calendar now = Calendar.getInstance();
       SimpleDateFormat simpledate = new SimpleDateFormat("yyyy-MM-dd");
       Date date = null;
       try {
           date = simpledate.parse(dateTime);
       } catch (ParseException ex) {
           System.out.println("日期格式不符合要求：" + ex.getMessage());
           return null;
       }
       now.setTime(date);
       int year = now.get(Calendar.YEAR);
       int month = now.get(Calendar.MONTH);
       int day = now.get(Calendar.DAY_OF_MONTH) - 1;
       now.set(year, month, day);
       String time = simpledate.format(now.getTime());
       return time;
    }
    /**
     * 计算指定日期的上一年
     * @param dateTime
     * @日期，格式为：yyyy-MM-dd
     * @return
     */
    public static String getBeforeYear(String dateTime) {
        if( dateTime == null) return null;
        Calendar now = Calendar.getInstance();
        SimpleDateFormat simpledate = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpledate.parse(dateTime);
        } catch (ParseException ex) {
            System.out.println("日期格式不符合要求：" + ex.getMessage());
            return null;
        }
        now.setTime(date);
        int year = now.get(Calendar.YEAR) - 1;
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH);
        now.set(year, month, day);
        String time = simpledate.format(now.getTime());
        return time;
    }
    
    /**：
     * 2008-10-20->2008年10月20日
     */
    public static String convertChar2ChineseChar2(String strDate) {
        if (StringUtils.isEmpty(strDate)) {
            return "";
        }
        String year = strDate.substring(0, 4);
        String month = strDate.substring(5, 7);
        String day = strDate.substring(8, 10);
        String ret = year + "年" + month + "月" + day + "日";
        return ret;
    }
    public static int compareTo(String oneDateStr, String twoDateStr) {
    	try {
        	Date oneDate = strDate2Date(oneDateStr);
        	Date twoDate = strDate2Date(twoDateStr);
        	return oneDate.compareTo(twoDate);
		} catch (Exception e) {
		}
    	return 0;
    }

	 /** 
    * 获得指定日期的前一天 
    *  
    * @param specifiedDay 
    * @return 
    * @throws Exception 
    */  
   public static String getSpecifiedDayBefore(String specifiedDay) {//可以用new Date().toLocalString()传递参数  
       Calendar c = Calendar.getInstance();  
       Date date = null;  
       try {  
           date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);  
       } catch (ParseException e) {  
           e.printStackTrace();  
       }  
       c.setTime(date);  
       int day = c.get(Calendar.DATE);  
       c.set(Calendar.DATE, day - 1);  
 
       String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c  
               .getTime());  
       return dayBefore;  
   }  
 
   /** 
    * 获得指定日期的后一天 
    *  
    * @param specifiedDay 
    * @return 
    */  
   public static Date getSpecifiedDayAfter(String specifiedDay) {  
       Calendar c = Calendar.getInstance();  
       Date date = null;  
       try {  
           date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);  
       } catch (ParseException e) {  
           e.printStackTrace();  
       }  
       c.setTime(date);  
       int day = c.get(Calendar.DATE);  
       c.set(Calendar.DATE, day + 1);  
 
//       String dayAfter = new SimpleDateFormat("yyyy-MM-dd")  
//               .format(c.getTime());  
       return c.getTime();  
   }   
   
   
   

	/**
	 * 将字符串日期格式转化成另一种日期格式
	 * 
	 * @param strDate
	 *            :字符日期
	 * @param dateFormat
	 *            ：字符日期的格式
	 * @param pformat
	 *            ：需要格式化后的日期格式
	 * @return
	 */
	public static String getStringToString(String strDate, String dateFormat,
			String pformat) {
		return getDateToString(getStringToDate(dateFormat, strDate), pformat);

	}

	/**
	 * 系统日期转化成字符串
	 * 
	 * @param format
	 * @return
	 */
	public static String getSystemDateToString(String format) {
		return getDateToString(new Date(), format);
	}

	/**
	 * 日期转化成str字符串
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getDateToString(Date date, String format) {
		if (date == null)
			return null;
		SimpleDateFormat siFormat = new SimpleDateFormat(format);
		try {
			return siFormat.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	/**
	 * 日期转化成str字符串(如果出错。或者为空则返回第三个参数的默认对象)
	 * 
	 * @param date
	 * @param format
	 * @param str
	 * @return
	 */
	public static String getDateToString(Date date, String format,String str) {
		if (date == null)
			return str;
		SimpleDateFormat siFormat = new SimpleDateFormat(format);
		try {
			return siFormat.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	

	/**
	 * 系统日期的前n日
	 * 
	 * @param dateTime
	 *            ：待处理的日期
	 * @param n
	 *            ：加减天数
	 * @return
	 */
	public static String getSystemDateToYesterday(int n, String format) {
		try {
			return getDateToString(new Date(getSystemDateToDate(format)
					.getTime() - getTime(n)), format);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 读取系统日期的年月份
	 * 
	 * @param format
	 * @param n
	 *            :正数为后几月，负数为前几月
	 * @return
	 */
	public static String getSysBeforMoth(String format, int n) {
		return new SimpleDateFormat(format).format(getSysBeforMoth(n));
	}

	/**
	 * 系统日期int格式
	 * 
	 * @param format
	 * @return
	 */
	public static int getSystemDateInteger(String format) {
		return getDateInteger(format, new Date());

	}

	/**
	 * 读取日期中的年份
	 * 
	 * @param format
	 * @param date
	 * @return
	 */
	public static int getDateInteger(String format, Date date) {
		try {
			return Integer.parseInt(new SimpleDateFormat(format).format(date));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 月份的最后一天
	 * @param date
	 * @return
	 */
	public static int getMothLastDate(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 距离n天的毫秒数
	 * 
	 * @param n
	 * @return
	 */
	public static long getTime(int n) {
		return n * 24 * 60 * 60 * 1000;
	}

	/**
	 * 系统日期的前n日、或者后n日
	 * 
	 * @param dateTime
	 *            ：待处理的日期
	 * @param n
	 *            ：加减天数(正=后，负=前)
	 * @return
	 */
	public static Date getBeforDay(int n, Date date) {
		try {
			return new Date(date.getTime() + getTime(n));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * N个月的最后一天
	 * @return
	 */
	public static Date getBeforMathLastDay(int n){
		 Calendar c = Calendar.getInstance();
		 c.add(Calendar.MONTH, n);
		 //得到一个月最后一天日期(31/30/29/28)
		 int MaxDay=c.getActualMaximum(Calendar.DAY_OF_MONTH);
		 //按你的要求设置时间
		 c.set( c.get(Calendar.YEAR), c.get(Calendar.MONTH), MaxDay, 23, 59, 59);
		 return c.getTime();
	}

	/**
	 * 读取日期月份
	 * 
	 * @param format
	 * @param n
	 *            :正数为后几月，负数为前几月
	 * @return
	 */
	public static Date getSysBeforMoth(int n) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, n);
		return c.getTime();
	}
	

	/**
	 * 读取系统日期
	 * 
	 * @param format
	 * @return
	 */
	public static Date getSystemDateToDate(String format) {
		return getStringToDate(format,
				new SimpleDateFormat(format).format(new Date()));
	}

	/**
	 * 字符串日期转化成日期对象Date
	 * 
	 * @param format
	 * @param date
	 * @param locale
	 * @return
	 */
	public static Date getStringToDate(String format, String date, Locale locale) {
		try {
			if( date!=null && date.trim().length()>0)return new SimpleDateFormat(format, locale).parse(date);
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 字符串日期转化成日期对象Date（本地日期对象）
	 * 
	 * @param format
	 * @param date
	 * @return
	 */
	public static Date getStringToDate(String format, String date) {
		return getStringToDate(format, date, Locale.CHINA);
	}

	/**
	 * 比较两个日期是否相等
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isEquals(Date date1, Date date2) {
		return isEqualsYYYYMMDD(getDateToString(date1, yyyy_MM_dd_HH_mm_ss),
				getDateToString(date2, yyyy_MM_dd_HH_mm_ss));
	}

	/**
	 * 当前日期是否与系统日期相等
	 * 
	 * @param date
	 * @param systemformat
	 * @return
	 */
	public static boolean isEqualsYYYYMMDDToday(String date, String systemformat) {
		return isEqualsYYYYMMDD(date, getSystemDateToString(systemformat));
	}

	/**
	 * 当前日期是否与系统日期相等
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isEqualsYYYYMMDDToday(Date date) {
		return isEqualsYYYYMMDD(date, new Date());
	}

	/**
	 * 比较两个日期是否相等
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isEqualsYYYYMMDD(Date date1, Date date2) {
		return isEqualsYYYYMMDD(getDateToString(date1, yyyy_MM_dd),
				getDateToString(date2, yyyy_MM_dd));
	}

	/**
	 * 比较两个日期字符串是否相等，需要确定两个日期的字符格式
	 * 
	 * @param str1
	 * @param str1Format
	 * @param str2
	 * @param str2Format
	 * @return
	 */
	public static boolean isEqualsYYYYMMDD(String str1, String str1Format,
			String str2, String str2Format) {
		return isEqualsYYYYMMDD(
				getStringToString(str1, str1Format, yyyy_MM_dd),
				getStringToString(str2, str2Format, yyyy_MM_dd));
	}

	/**
	 * 比较两个日期字符串是否相等，需要确定两个日期的字符格式
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean isEqualsYYYYMMDD(String str1, String str2) {
		if (str1 == null) {
			if (str2 == null)
				return true;
			return false;
		}
		return str1.equals(str2);
	}

	/**
	 * 比较日期大小
	 * 
	 * @param date1
	 * @param date2
	 * @return true：date1大于/等于data2；false：date1小于data2
	 */
	public static boolean isMoreSize(Date date1, Date date2) {
		if (date1 == null)
			return false;
		if (!date1.before(date2))
			return true;
		return false;
	}

}

