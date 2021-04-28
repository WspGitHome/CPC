package com.wanfangdata.cpc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author itguang
 * @create 2018-01-04 13:36
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfig{


    @Bean
    public Docket ProductApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(false)
                .pathMapping("/")
                .select()
                .build()
                .apiInfo(apiInfo());
    }

        private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("公共文化检索服务接口文档")
                .description("检索，用户信息，配置信息")
                .contact("FLY")
                .version("1.0")
                .build();
    }


}
