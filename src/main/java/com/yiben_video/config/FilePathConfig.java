package com.yiben_video.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 王飞
 * @create 2021/6/15 16:03
 * @emai 936886169@qq.com
 */
@Configuration
public class FilePathConfig implements WebMvcConfigurer {

    @Value("${video.path}")
    private String path;

    @Value("${video.address}")
    private String address;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(path).addResourceLocations("file:" + address);
    }

}
