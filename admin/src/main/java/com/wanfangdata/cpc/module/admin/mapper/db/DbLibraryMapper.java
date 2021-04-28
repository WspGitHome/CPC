package com.wanfangdata.cpc.module.admin.mapper.db;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wanfangdata.cpc.module.admin.model.db.DbLibrary;
import com.wanfangdata.cpc.module.admin.vo.db.DblibraryConditionVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.io.Serializable;
import java.util.List;

public interface DbLibraryMapper extends BaseMapper<DbLibrary> {

    IPage<DbLibrary> findByCondition(@Param("page") IPage<DbLibrary> page, @Param("vo") DblibraryConditionVo vo);

    int deleteBatch(Integer[] ids);

    @Select("SELECT id, name  FROM db_library")
    List<DbLibrary> selectDbName();

    List<DbLibrary> findAll(@Param("type") Integer type);

    DbLibrary selectById(@Param("id") Serializable id);
}
