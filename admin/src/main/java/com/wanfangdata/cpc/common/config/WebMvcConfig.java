package com.wanfangdata.cpc.common.config;

import com.wanfangdata.cpc.common.config.properties.FileUploadProperties;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.MultipartConfigElement;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Nobita
 * @date 2020/4/18 11:58 上午
 */
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties(FileUploadProperties.class)
public class WebMvcConfig implements WebMvcConfigurer {

    private final FileUploadProperties fileUploadProperties;

    private final static String FILE_PATARN="/**";

    /**
     * 配置本地文件上传的虚拟路径
     * 备注：这是一种图片上传访问图片的方法，实际上也可以使用nginx反向代理访问图片
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(fileUploadProperties.getAccessPathPattern()+FILE_PATARN, fileUploadProperties.getDeployPathPattern()+FILE_PATARN)
                .addResourceLocations("file:" + fileUploadProperties.getUploadFolder(), "file:" + fileUploadProperties.getDeployFolder());
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //文件最大
        factory.setMaxFileSize(DataSize.ofGigabytes(1));
        factory.setMaxRequestSize(DataSize.ofGigabytes(1));
        return factory.createMultipartConfig();
    }
}
