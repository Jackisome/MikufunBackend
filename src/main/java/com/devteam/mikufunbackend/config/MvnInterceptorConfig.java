package com.devteam.mikufunbackend.config;

import com.devteam.mikufunbackend.interceptor.SignInterceptor;
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
    private SignInterceptor signInterceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(signInterceptor)
                .addPathPatterns("/api/v1/**")
                .addPathPatterns("/test/**")
                .excludePathPatterns("/api/v1/login");
        super.addInterceptors(registry);
    }
}
