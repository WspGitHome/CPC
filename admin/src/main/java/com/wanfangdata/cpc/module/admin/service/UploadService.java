package com.wanfangdata.cpc.module.admin.service;

import com.wanfangdata.cpc.module.admin.vo.UploadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author linzhaoguan
 * @date 2020/3/31 11:12 上午
 */
public interface UploadService {

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    UploadResponse upload(MultipartFile file);
    /**
     * 上传文件
     *
     * @param file
     * @param type
     * @return
     */
    UploadResponse upload(MultipartFile file,Integer type);

    /**
     * 获取云存储配置内容
     *
     * @return
     */
    List<String> getConfig();

}
