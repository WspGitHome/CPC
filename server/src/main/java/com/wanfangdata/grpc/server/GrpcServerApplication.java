package com.wanfangdata.grpc.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableCaching
public class GrpcServerApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(GrpcServerApplication.class);
        application.setWebApplicationType(WebApplicationType.NONE);
        application.run();
    }

    @PostConstruct
    private static void initJSONConfig() {
//        JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.UseSingleQuotes.getMask();
        JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.NotWriteRootClassName.getMask();
        JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.WriteNullNumberAsZero.getMask();
        JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.WriteNullStringAsEmpty.getMask();
    }
}
