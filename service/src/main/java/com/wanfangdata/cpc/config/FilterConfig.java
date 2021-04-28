package com.wanfangdata.cpc.config;

import com.wanfangdata.cas.filter.IpAuthenticationFilter;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.Map;

/**
 * @author itguang
 * @create 2018-01-04 13:36
 **/
@Data
@Configuration
@Order(0)
@ConfigurationProperties(prefix = "wanfang.cpc")
public class FilterConfig {

    public String ipLoginUrl;
    public String serverName;
    public String ipLogin;
    public String notUrl;

    @Bean
    public FilterRegistrationBean myFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        // 拦截路径
        registration.addUrlPatterns("/*");
        registration.addInitParameter("ipLoginUrl",ipLoginUrl);
        registration.addInitParameter("serverName",serverName);
        registration.addInitParameter("ipLogin",ipLogin);
        registration.addInitParameter("notUrl",notUrl);
        registration.setFilter(new IpAuthenticationFilter());
        // 拦截器名称
        registration.setName("IpAuthenticationFilter");
        registration.setOrder(1);// 顺序
        return registration;
    }
}
