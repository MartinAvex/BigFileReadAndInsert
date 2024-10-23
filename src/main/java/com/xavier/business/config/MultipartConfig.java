package com.xavier.business.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * @description: 文件解析器配置，设置内存大小和文件大小限制
 * @author: ex_wuzr11
 * @date: 2024/10/22 10:36
 */
@Configuration
public class MultipartConfig {

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver getCommonsMultipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(300 * 1024 * 1024); //最大内存大小
        multipartResolver.setMaxInMemorySize(1024); //上传文件最小限制
        return multipartResolver;
    }

}
