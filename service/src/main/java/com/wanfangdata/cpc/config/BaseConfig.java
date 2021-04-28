package com.wanfangdata.cpc.config;

import com.wanfangdata.cpc.entity.Domain;
import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;


@Data
@Configuration
@Order(0)
@ConfigurationProperties(prefix = "wanfang.cpc")
public class BaseConfig {

    private String domain;
    private String fulltext;
    private String login;
    private String logout;
    private String registered;
    private String imageserver;

    @Bean
    public Domain domain(){
        return new Domain(domain,fulltext,login,logout,registered,imageserver);
    }

}
