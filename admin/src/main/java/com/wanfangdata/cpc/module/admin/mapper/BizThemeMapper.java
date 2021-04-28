package com.wanfangdata.cpc.module.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wanfangdata.cpc.module.admin.model.BizTheme;

/**
 * @author Linzhaoguan
 * @version V1.0
 * @date 2019年9月11日
 */
public interface BizThemeMapper extends BaseMapper<BizTheme> {

    int setInvaid();

    int updateStatusById(Integer id);

    int deleteBatch(Integer[] ids);
}