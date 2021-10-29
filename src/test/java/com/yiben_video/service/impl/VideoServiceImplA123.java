package com.yiben_video.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yiben_video.common.Result;
import com.yiben_video.dao.VideoDao;
import com.yiben_video.pojo.VideoEntity;
import com.yiben_video.service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author 王飞
 * @create 2021/6/2 14:20
 * @emai 936886169@qq.com
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class VideoServiceImplA123 {

    @Resource
    VideoDao videoDao;
    @Resource
    VideoService videoService;
    @Test
    void allVideo() {
        List<VideoEntity> list = videoDao.findAll();
        String l = JSONObject.toJSONString(list);
        System.out.println(l);
    }
    @Test
    void del(){
        VideoEntity videoEntity = videoService.FindOneVideo(120);
        System.out.println(videoEntity.getVideoUrl().substring(32,videoEntity.getVideoUrl().length()));
    }
    @Test
    void ceshi(){
        String s = "http://192.168.31.117:7879/file/6dd7df892f3a46de8a346390035cb32e.mp4";
        /*String regex="file/(.*?).mp4";
        Pattern p= Pattern.compile(regex);
        Matcher m=p.matcher(s);
        while(m.find()){
            System.out.println(m.group(1));
        }*/
        String R =s.substring(0,s.indexOf("e"));
        String str2 = s.substring(R.length()+2,s.length());
        System.out.println(str2);



    }
}