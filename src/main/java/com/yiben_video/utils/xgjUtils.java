package com.yiben_video.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 王飞
 * @create 2021/7/1 14:49
 * @emai 936886169@qq.com
 * 小的工具类
 */
public class xgjUtils {
    public static String zfc(String s){
        //String regex="file/(.*?).mp4";
        String R =s.substring(0,s.indexOf("e"));
        String str2 = s.substring(R.length()+2,s.length());
        System.out.println(str2);
        return str2;
    }
}
