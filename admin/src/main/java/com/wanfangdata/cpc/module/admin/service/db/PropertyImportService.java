package com.wanfangdata.cpc.module.admin.service.db;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wanfangdata.cpc.module.admin.model.db.DbColumn;
import com.wanfangdata.cpc.module.admin.model.db.DbProperty;
import com.wanfangdata.cpc.module.admin.model.db.DbPropertyImport;
import com.wanfangdata.cpc.module.admin.vo.db.ColumnConditionVo;
import com.wanfangdata.cpc.module.admin.vo.db.PropertyImportConditionVo;

import java.util.List;
import java.util.Map;

/**
 * @author Linzhaoguan
 * @version V1.0
 * @date 2019年9月11日
 */
public interface PropertyImportService extends IService<DbPropertyImport> {

    List<DbPropertyImport> findByCondition(IPage<DbPropertyImport> page, PropertyImportConditionVo vo);

    DbPropertyImport getById(Integer id);

}
