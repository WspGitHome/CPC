package com.wanfangdata.cpc.module.admin.controller;

import com.wanfangdata.cpc.common.util.CoreConst;
import com.wanfangdata.cpc.common.util.ResultUtil;
import com.wanfangdata.cpc.module.admin.service.UploadService;
import com.wanfangdata.cpc.module.admin.vo.UploadResponse;
import com.wanfangdata.cpc.module.admin.vo.base.ResponseVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * 后台文件上传接口、云存储配置
 *
 * @author Linzhaoguan
 * @version V1.0
 * @date 2019年9月11日
 */
@Slf4j
@Controller
@RequestMapping("/attachment")
@AllArgsConstructor
public class UploadController {

    private final UploadService uploadService;

    /**
     * 类型 1：前端模板路径  2：文章资源路径  3：栏目资源路径  4：资源导入路径 5.其他
     */
    @ResponseBody
    @PostMapping("/upload/{type}")
    public UploadResponse upload(@RequestParam(value = "file", required = false) MultipartFile file,@PathVariable("type") Integer type) {
        return uploadService.upload(file,type);
    }

    @ResponseBody
    @PostMapping("/uploadForCkEditor")
    public Object upload2QiniuForMd(@RequestParam("upload") MultipartFile file) {
        Map<String, Object> resultMap = new HashMap<>(2);
        UploadResponse responseVo = uploadService.upload(file,CoreConst.UPLOAD_TYPE_CONTENT);
        if (CoreConst.SUCCESS_CODE.equals(responseVo.getStatus())) {
            resultMap.put("uploaded", 1);
            resultMap.put("url", responseVo.getUrl());
            return resultMap;
        }
        return resultMap;
    }

    @GetMapping("/config")
    public String config(Model model) {
        model.addAttribute("uploadConfig", uploadService.getConfig());
        return CoreConst.ADMIN_PREFIX + "upload/config";
    }

}
