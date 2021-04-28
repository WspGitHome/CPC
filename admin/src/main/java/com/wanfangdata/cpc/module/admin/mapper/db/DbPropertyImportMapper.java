package com.wanfangdata.cpc.module.admin.mapper.db;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wanfangdata.cpc.module.admin.model.db.DbColumn;
import com.wanfangdata.cpc.module.admin.model.db.DbPropertyImport;
import com.wanfangdata.cpc.module.admin.vo.db.PropertyImportConditionVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DbPropertyImportMapper extends BaseMapper<DbPropertyImport> {

    List<DbPropertyImport> findByCondition(@Param("page") IPage<DbPropertyImport> page,@Param("vo")  PropertyImportConditionVo vo);

    DbPropertyImport getById(Integer id);
}
