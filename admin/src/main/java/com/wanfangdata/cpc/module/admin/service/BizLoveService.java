package com.wanfangdata.cpc.module.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wanfangdata.cpc.module.admin.model.BizLove;

/**
 * @author Linzhaoguan
 * @version V1.0
 * @date 2019年9月11日
 */
public interface BizLoveService extends IService<BizLove> {

    BizLove checkLove(Integer bizId, String userIp);
}
