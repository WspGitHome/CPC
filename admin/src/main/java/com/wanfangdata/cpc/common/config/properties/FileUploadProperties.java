package com.wanfangdata.cpc.common.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Nobita
 * @date 2020/4/18 12:10 下午
 */
@Data
@ConfigurationProperties(prefix = "file")
public class FileUploadProperties {
    /**
     *
     * 资源访问路径
     * */
    private String accessPathPattern;
    /**
     *
     * 模板访问路径
     * */
    private String deployPathPattern;
    /**
     * 文件上传根路径
     *
     * */
    private String uploadFolder;
    /**
     * 文件上传根路径
     *
     * */
    private String deployFolder;

}
