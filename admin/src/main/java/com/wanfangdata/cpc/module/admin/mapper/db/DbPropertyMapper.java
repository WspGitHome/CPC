package com.wanfangdata.cpc.module.admin.mapper.db;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wanfangdata.cpc.module.admin.model.db.DbIdForCategory;
import com.wanfangdata.cpc.module.admin.model.db.DbProperty;
import com.wanfangdata.cpc.module.admin.model.db.DbPropertyList;
import com.wanfangdata.cpc.module.admin.vo.db.PropertyConditionVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface DbPropertyMapper extends BaseMapper<DbProperty> {

    Integer saveBatch(@Param("table")String table,@Param("feilds") List<String> feilds, @Param("values")List<List<Object>> values);

    Integer updataColumn(@Param("table")String table,@Param("feilds") Map<String,String>  feilds);

    @Select("show full columns from ${table}")
    List<Map<String ,Object>> selectSchama(@Param("table")String table);

    List<Map<String,Object>> findByPage(@Param("table")String table,@Param("start")Integer start,@Param("size")Integer size,@Param("batchId")Integer batchId);

    Integer selectCount(@Param("table")String table,@Param("batchId")Integer batchId);

    List<DbIdForCategory> selectIdForCategory(@Param("table") String table, @Param("ids")List<String> ids);
    /***
     * @description 获取资源数据
     * @param
     * @return java.util.List<com.wanfangdata.cpc.module.admin.model.db.DbProperty>
     * @authors rongrong
     * @date 2020/8/13
     * @modified by
     * @version 1.0
     */
    @Select("SELECT id,property_name, property_table_name  FROM db_property")
    List<DbProperty> selectProperty();
    /***
     * @description 资源列表
     * @param page
     * @param vo
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.wanfangdata.cpc.module.admin.model.db.DbPropertyList>
     * @authors rongrong
     * @date 2020/8/13
     * @modified by
     * @version 1.0
     */
    IPage<DbPropertyList> findByCondition(@Param("page") IPage<DbPropertyList> page,@Param("vo") PropertyConditionVo vo);
    /***
     * @description 删除资源数据
     * @param proId
     * @param proType
     * @return int
     * @authors rongrong
     * @date 2020/8/13
     * @modified by
     * @version 1.0
     */
    int deletePro(String proId,String proType);
}
