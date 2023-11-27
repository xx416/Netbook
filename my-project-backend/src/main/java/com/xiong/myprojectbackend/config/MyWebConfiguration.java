package com.xiong.myprojectbackend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 10371
 * @Description
 * @create 2023/11/13 16:44:38
 */
@Configuration
public class MyWebConfiguration implements WebMvcConfigurer {

    /**
     * 上传地址
     */
    @Value("${file.upload.pathCover}")
    private String filePathCover;
    /**
     * 上传地址
     */
    @Value("${file.upload.pathHead}")
    private String filePathHead;
    /**
     * 显示相对地址
     */
    @Value("${file.upload.relative}")
    private String fileRelativePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(fileRelativePath)
                .addResourceLocations("file:/" + filePathCover)
                .addResourceLocations("file:/" + filePathHead);
    }

}