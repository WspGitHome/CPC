package com.wanfangdata.cpc.config;

import com.wanfangdata.cpc.interceptor.LogInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author itguang
 * @create 2018-01-04 13:36
 **/
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //这里可以添加多个拦截器
        registry.addInterceptor(new LogInterceptor()).addPathPatterns("/**");
     /*   registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/reading","/download","/doTransaction");
        registry.addInterceptor(new PowerInterceptor()).addPathPatterns("/reading","/download","/doTransaction");*/
        super.addInterceptors(registry);
    }

}
