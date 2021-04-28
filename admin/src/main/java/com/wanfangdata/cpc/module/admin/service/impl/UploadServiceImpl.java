package com.wanfangdata.cpc.module.admin.service.impl;

import com.wanfangdata.cpc.common.annotation.Cache;
import com.wanfangdata.cpc.common.config.properties.FileUploadProperties;
import com.wanfangdata.cpc.common.util.*;
import com.wanfangdata.cpc.exception.UploadFileNotFoundException;
import com.wanfangdata.cpc.module.admin.service.UploadService;
import com.wanfangdata.cpc.module.admin.vo.UploadResponse;
import com.wanfangdata.cpc.module.admin.vo.base.ResponseVo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author linzhaoguan
 * @date 2020/3/31 2:41 下午
 */
@Slf4j
@Service
public class UploadServiceImpl implements UploadService {

    @Autowired
    private FileUploadProperties fileUploadProperties;

    @Override
    @SneakyThrows
    public UploadResponse upload(MultipartFile file) {
        return upload(file, CoreConst.UPLOAD_TYPE_OTHER);
    }

    @Override
    @SneakyThrows
    public UploadResponse upload(MultipartFile file, Integer type) {
        if (file == null || file.isEmpty()) {
            throw new UploadFileNotFoundException(UploadResponse.ErrorEnum.FILE_NOT_FOUND.msg);
        }
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf('.')).toLowerCase();

        String url = null;
        ResponseVo<?> responseVo = null;
        String middlePath = File.separator +fileUploadProperties.getAccessPathPattern();
        switch (type) {
            case CoreConst.UPLOAD_TYPE_FORMWORK:
                middlePath += File.separator + CoreConst.FORMWORK;
                break;
            case CoreConst.UPLOAD_TYPE_CONTENT:
                middlePath += File.separator + CoreConst.CONTENT;
                break;
            case CoreConst.UPLOAD_TYPE_COLUMN:
                middlePath += File.separator + CoreConst.COLUMN;
                break;
            case CoreConst.UPLOAD_TYPE_PROPERTY:
                middlePath += File.separator + CoreConst.PROPERTY;
                break;
            default:
                middlePath += File.separator + CoreConst.OTHER;
        }
        String relativePath = FileUploadUtil.uploadLocal(file, fileUploadProperties.getUploadFolder() + middlePath);
        url = middlePath + File.separator + relativePath;
        responseVo = ResultUtil.success();

        if (responseVo.getStatus().equals(CoreConst.SUCCESS_CODE)) {
            return UploadResponse.success(url, originalFilename, suffix, url, CoreConst.SUCCESS_CODE);
        } else {
            return UploadResponse.failed(originalFilename, CoreConst.FAIL_CODE, responseVo.getMsg());
        }
    }

    @Override
    public List<String> getConfig() {
        return Arrays.asList(new String[]{CoreConst.FORMWORK, CoreConst.COLUMN, CoreConst.CONTENT, CoreConst.PROPERTY, CoreConst.OTHER});
    }


}
