package com.devteam.mikufunbackend.feign.config;

import com.devteam.mikufunbackend.feign.ResourceInfoClient;
import com.devteam.mikufunbackend.feign.ResourceClient;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

    @Value("${dandanplay.url}")
    private String dandanPlayUrl;

    @Bean
    public ResourceClient resourceClient() {
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .client(new OkHttpClient())
                .target(ResourceClient.class, "https://k.dandanplay.workers.dev");
    }

    @Bean
    public ResourceInfoClient resourceInfoClient() {
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .client(new OkHttpClient())
                .target(ResourceInfoClient.class, dandanPlayUrl);
    }

}
