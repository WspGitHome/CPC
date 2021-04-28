package com.wanfangdata.cpc.module.admin.service.db;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wanfangdata.cpc.module.admin.model.db.DbTemplateInfo;

import java.util.List;

/**
 * @Packagename com.wanfangdata.cpc.module.admin.service.db
 * @Classname DbTemplateInfoService
 * @Description
 * @Authors Mr.Wu
 * @Date 2020/08/07 09:15
 * @Version 1.0
 */
public interface DbTemplateInfoService  extends IService<DbTemplateInfo> {

    List<DbTemplateInfo> selectList();
}
