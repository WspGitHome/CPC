package com.wanfangdata.grpc.server.config;

import com.wanfangdata.api.chain.MockFilterChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

/**
 *
 *@author FLY
 *@date 2019-11-1
 *
 */
@Configuration
@ImportResource({"classpath:search.xml"})
public class SpringBeanConfig {
    @Autowired
    Map<String,MockFilterChain> preFilter;
}
