package com.wanfangdata.cpc.module.admin.mapper.db;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wanfangdata.cpc.module.admin.model.db.DbTemplateInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Packagename com.wanfangdata.cpc.module.admin.mapper.db
 * @Classname DbFTemplateInfoMapper
 * @Description
 * @Authors Mr.Wu
 * @Date 2020/08/07 09:31
 * @Version 1.0
 */
public interface DbFTemplateInfoMapper  extends BaseMapper<DbTemplateInfo>{
    /**
     * @param
     * @return java.util.List<com.wanfangdata.cpc.module.admin.model.db.DbTemplateInfo>
     * @description 获取所有数据库模板
     * @authors Mr.Wu
     * @date 2020/08/07
     * @modified by
     * @version 1.0
     **/
    @Select("select * from db_template_info")
    List<DbTemplateInfo> selectList();

    DbTemplateInfo selectById(@Param("id") Integer id);
}
