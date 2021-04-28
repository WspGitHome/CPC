package com.wanfangdata.cpc.module.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanfangdata.cpc.module.admin.mapper.BizLoveMapper;
import com.wanfangdata.cpc.module.admin.model.BizLove;
import com.wanfangdata.cpc.module.admin.service.BizLoveService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Linzhaoguan
 * @version V1.0
 * @date 2019年9月11日
 */
@Service
@AllArgsConstructor
public class BizLoveServiceImpl extends ServiceImpl<BizLoveMapper, BizLove> implements BizLoveService {

    private final BizLoveMapper loveMapper;

    @Override
    public BizLove checkLove(Integer bizId, String userIp) {
        return loveMapper.checkLove(bizId, userIp);
    }
}
