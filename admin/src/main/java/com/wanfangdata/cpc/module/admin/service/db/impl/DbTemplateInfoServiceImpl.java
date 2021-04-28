package com.wanfangdata.cpc.module.admin.service.db.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanfangdata.cpc.module.admin.mapper.db.DbFTemplateInfoMapper;
import com.wanfangdata.cpc.module.admin.model.db.DbTemplateInfo;
import com.wanfangdata.cpc.module.admin.service.db.DbTemplateInfoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Packagename com.wanfangdata.cpc.module.admin.service.db.impl
 * @Classname DbTemplateInfoServiceImpl
 * @Description
 * @Authors Mr.Wu
 * @Date 2020/08/07 09:21
 * @Version 1.0
 */
@Service
@AllArgsConstructor
public class DbTemplateInfoServiceImpl  extends ServiceImpl<DbFTemplateInfoMapper, DbTemplateInfo> implements DbTemplateInfoService {

    private final DbFTemplateInfoMapper dbFTemplateInfoMapper;

    @Override
    public List<DbTemplateInfo> selectList() {
        return dbFTemplateInfoMapper.selectList();
    }
}
