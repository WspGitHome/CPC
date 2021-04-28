package com.wanfangdata.cpc.module.admin.controller;

import com.wanfangdata.cpc.common.util.ResultUtil;
import com.wanfangdata.cpc.module.admin.service.SysConfigService;
import com.wanfangdata.cpc.module.admin.vo.base.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 后台网站信息配置
 *
 * @author Linzhaoguan
 * @version V1.0
 * @date 2019年9月11日
 */
@Controller
public class SiteInfoController {
    @Autowired
    private SysConfigService configService;

    @PostMapping("/siteinfo/edit")
    @ResponseBody
    public ResponseVo save(@RequestParam Map<String, String> map) {
        try {
            for (String key : map.keySet()) {
                configService.updateByKey(key, map.get(key));
            }
            return ResultUtil.success("保存网站信息成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error("保存网站信息失败");
        }
    }
}
