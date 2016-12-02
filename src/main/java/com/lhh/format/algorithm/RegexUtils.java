package com.lhh.format.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式
 * @author hwaggLee
 * @createDate 2016年11月30日
 */
public class RegexUtils {


    /**
     * html标签从字符串中读取图片属性
     * @param s
     * @return
     */
    public static List<String> getImg(String s)
    {
        String regex;
        List<String> list = new ArrayList<String>();
        regex = "<(img|IMG)(.*?)(/>|></img>|>)";
        Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
        Matcher ma = pa.matcher(s);
        while (ma.find())
        {
            list.add(ma.group());
        }
        return list;
    }

    /**
     * html标签从字符串中读取视频属性
     * @param s
     * @return
     */
    public static List<String> getVideo(String s)
    {
        String regex;
        List<String> list = new ArrayList<String>();
        regex = "<(video|VIDEO)(.*?)(/>|></video>|>)";
        Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
        Matcher ma = pa.matcher(s);
        while (ma.find())
        {
            list.add(ma.group());
        }
        return list;
    }

    /**
     * html标签从字符串中读取超链接属性
     * @param s
     * @return
     */
    public static List<String> getHref(String s)
    {
        String regex;
        List<String> list = new ArrayList<String>();
        regex = "<(a|A)(.*?)(/>|></a>|>)";
        Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
        Matcher ma = pa.matcher(s);
        while (ma.find())
        {
            list.add(ma.group());
        }
        return list;
    }
}
