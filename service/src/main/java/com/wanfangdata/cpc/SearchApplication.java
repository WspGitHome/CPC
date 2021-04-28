package com.wanfangdata.cpc;

import com.wanfangdata.cpc.utils.SpringContextUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableCaching
public class SearchApplication {

	public static void main(String[] args) {
		ApplicationContext context=SpringApplication.run(SearchApplication.class, args);
		SpringContextUtil.setApplicationContext(context);
	}
}
