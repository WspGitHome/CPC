package com.wanfangdata.cpc.module.admin.service.db;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wanfangdata.cpc.module.admin.model.db.DbIdForCategory;
import com.wanfangdata.cpc.module.admin.model.db.DbProperty;
import com.wanfangdata.cpc.module.admin.model.db.DbPropertyList;
import com.wanfangdata.cpc.module.admin.vo.db.PropertyConditionVo;

import java.util.List;
import java.util.Map;

/**
 * @author Linzhaoguan
 * @version V1.0
 * @date 2019年9月11日
 */
public interface PropertyService extends IService<DbProperty> {

    Integer saveBatch(String table,List<String> feilds,  List<List<Object>> values);

    Integer updataColumn(String table,Map<String,String> feilds);

    List<Map<String ,Object>> selectSchama(String table);

    List<Map<String,Object>> findByPage(String table,Integer start,Integer size,Integer batchId);

    Integer selectCount(String table,Integer batchId);

    List<DbIdForCategory> selectIdForCategory(String table,List<String> ids);
    /***
     * @description 获取资源类型
     * @param
     * @return java.util.List<com.wanfangdata.cpc.module.admin.model.db.DbProperty>
     * @authors rongrong
     * @date 2020/8/13
     * @modified by
     * @version 1.0
     */
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
    IPage<DbPropertyList> findByCondition(IPage<DbPropertyList> page, PropertyConditionVo vo);
    /***
     * @description 删除资源
     * @param proId
     * @param proType
     * @return int
     * @authors rongrong
     * @date 2020/8/13
     * @modified by
     * @version 1.0
     */
    int deletePro(String  proId,String proType);
}
