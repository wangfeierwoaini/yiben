package com.yiben_video.utils;

import java.util.UUID;

/**
 * @author 王飞
 * @create 2021/7/1 9:57
 * @emai 936886169@qq.com
 */
public class UUIDUtil {
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static void main(String[] args) {
        System.out.println("格式前的UUID ： " + UUID.randomUUID().toString());
        System.out.println("格式化后的UUID ：" + getUUID());
    }

}
