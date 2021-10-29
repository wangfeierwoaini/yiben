package com.yiben_video.utils;

import org.springframework.beans.factory.annotation.Value;

import java.io.File;

/**
 * @author 王飞
 * @create 2021/7/6 11:30
 * @emai 936886169@qq.com
 */
public class file {
    @Value("${video.address}")
    private String address;
    @Value("${url}")
    private String url;

    public static void updata(File file){
        String uuid = UUIDUtil.getUUID();

    }
}
