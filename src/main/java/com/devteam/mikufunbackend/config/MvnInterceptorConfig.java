package com.devteam.mikufunbackend.config;

import com.devteam.mikufunbackend.interceptor.UserInterceptor;
import com.devteam.mikufunbackend.interceptor.VisitorInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author Jackisome
 * @date 2021/9/27
 */
@Configuration
public class MvnInterceptorConfig extends WebMvcConfigurationSupport {

    @Autowired
    private UserInterceptor userInterceptor;

    @Autowired
    private VisitorInterceptor visitorInterceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(visitorInterceptor)
                .addPathPatterns("/api/v1/download/downloading")
                .addPathPatterns("/api/v1/download/finish")
                .addPathPatterns("/api/v1/download/finish/list")
                .addPathPatterns("/api/v1/download/finish/resource/**")
                .addPathPatterns("/api/v1/download/diskspace")
                .addPathPatterns("/api/v1/freedownload/finish")
                .addPathPatterns("/api/v1/play/file/**");
        registry.addInterceptor(userInterceptor)
                .addPathPatterns("/api/v1/**")
                .addPathPatterns("/test/**")
                .excludePathPatterns("/api/v1/login")
                .excludePathPatterns("/api/v1/download/downloading")
                .excludePathPatterns("/api/v1/download/finish")
                .excludePathPatterns("/api/v1/download/finish/list")
                .excludePathPatterns("/api/v1/download/finish/resource/**")
                .excludePathPatterns("/api/v1/download/diskspace")
                .excludePathPatterns("/api/v1/freedownload/finish")
                .excludePathPatterns("/api/v1/play/file/**")
                .excludePathPatterns("/api/v1/play/danmaku/v3");
        super.addInterceptors(registry);
    }
}
