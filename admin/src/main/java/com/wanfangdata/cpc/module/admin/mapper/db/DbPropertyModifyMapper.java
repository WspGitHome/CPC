package com.wanfangdata.cpc.module.admin.mapper.db;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wanfangdata.cpc.module.admin.model.db.DbLocalChronicle;
import com.wanfangdata.cpc.module.admin.model.db.DbLocalChronicleItem;
import com.wanfangdata.cpc.module.admin.model.db.DbPropertyList;
import com.wanfangdata.cpc.module.admin.model.db.DbPropertyModify;
import com.wanfangdata.cpc.module.admin.vo.db.PropertyModifyConditionVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface DbPropertyModifyMapper extends BaseMapper<DbPropertyModify> {
    /***
     * @description 新增资源记录表
     * @param propertyId
     * @return java.lang.Integer
     * @authors rongrong
     * @date 2020/8/19
     * @modified by
     * @version 1.0
     */
    Integer ifPropertyId(String propertyId);
    /***
     * @description  修改资源记录
     * @param propertyModify
     * @return boolean
     * @authors rongrong
     * @date 2020/8/19
     * @modified by
     * @version 1.0
     */
    boolean updateProperty(@Param("propertyModify")DbPropertyModify propertyModify);
    /***
     * @description 资源修改记录列表
     * @param page
     * @param vo
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.wanfangdata.cpc.module.admin.model.db.DbPropertyList>
     * @authors rongrong
     * @date 2020/8/20
     * @modified by
     * @version 1.0
     */
    IPage<DbPropertyList> findByCondition(@Param("page") IPage<DbPropertyList> page, @Param("vo") PropertyModifyConditionVo vo);
    /***
     * @description 根据表名查询所有的列名
     * @param proType
     * @return java.util.List<java.lang.String>
     * @authors rongrong
     * @date 2020/8/20
     * @modified by
     * @version 1.0
     */
    @Select("select column_name from information_schema.columns where table_name='${proType}'")
    List<String> findColumnNames(@Param("proType")String proType);
    /***
     * @description 根据条件获取对应的条件
     * @param startDate
     * @param endDate
     * @return java.util.List<java.util.List<java.lang.String>>
     * @authors rongrong
     * @date 2020/8/20
     * @modified by
     * @version 1.0
     */
    List<DbLocalChronicle> findLocalChronicleData( String startDate, String endDate);
    /*** 
     * @description 
     * @param startDate
     * @param endDate
     * @return java.util.List<com.wanfangdata.cpc.module.admin.model.db.DbLocalChronicleItem>
     * @authors rongrong
     * @date 2020/8/21
     * @modified by
     * @version 1.0
     */
    List<DbLocalChronicleItem> findItemData(String startDate, String endDate);
}
