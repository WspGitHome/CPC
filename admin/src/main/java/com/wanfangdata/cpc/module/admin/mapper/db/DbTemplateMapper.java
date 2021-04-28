package com.wanfangdata.cpc.module.admin.mapper.db;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wanfangdata.cpc.module.admin.model.db.DbTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.io.Serializable;
import java.util.List;


/**
 * @Packagename com.wanfangdata.cpc.module.admin.mapper.db
 * @Classname DbFTemplateInfoMapper
 * @Description
 * @Authors Mr.Wu
 * @Date 2020/08/07 09:31
 * @Version 1.0
 */
public interface DbTemplateMapper extends BaseMapper<DbTemplate> {

    @Override
    int insert(DbTemplate entity);

    @Override
    int deleteById(@Param("id") Serializable id);

    @Override
    int updateById(DbTemplate entity);

    IPage<DbTemplate> selectPage(IPage<DbTemplate> page, @Param("dbTemplate") DbTemplate dbTemplate);

    @Override
    DbTemplate selectById(@Param("id") Serializable id);

    @Select("select db_id from db_template")
    List<Integer> selectDbIds();
}
