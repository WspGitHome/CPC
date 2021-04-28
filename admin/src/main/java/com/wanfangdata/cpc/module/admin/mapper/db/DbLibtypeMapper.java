package com.wanfangdata.cpc.module.admin.mapper.db;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wanfangdata.cpc.module.admin.model.db.DbLibtype;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DbLibtypeMapper extends BaseMapper<DbLibtype> {

    @Select("SELECT * FROM db_libtype")
    List<DbLibtype> selecLibtypes();

}
