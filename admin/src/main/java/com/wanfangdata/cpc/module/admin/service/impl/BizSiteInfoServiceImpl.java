package com.wanfangdata.cpc.module.admin.service.impl;

import com.wanfangdata.cpc.module.admin.mapper.BizArticleMapper;
import com.wanfangdata.cpc.module.admin.service.BizSiteInfoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Linzhaoguan
 * @version V1.0
 * @date 2019年9月11日
 */
@Service
@AllArgsConstructor
public class BizSiteInfoServiceImpl implements BizSiteInfoService {

    private final BizArticleMapper bizArticleMapper;

    @Override
    public Map<String, Object> getSiteInfo() {
        return bizArticleMapper.getSiteInfo();
    }

}
