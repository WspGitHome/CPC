package com.wanfangdata.cpc.module.admin.service.db;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wanfangdata.cpc.module.admin.model.db.DbLocalChronicle;
import com.wanfangdata.cpc.module.admin.model.db.DbLocalChronicleItem;
import com.wanfangdata.cpc.module.admin.model.db.DbPropertyList;
import com.wanfangdata.cpc.module.admin.model.db.DbPropertyModify;
import com.wanfangdata.cpc.module.admin.vo.db.PropertyModifyConditionVo;

import java.util.List;


/**
 * @ProjectName: LocalChronicleGrpcSearch
 * @Package: com.wanfangdata.cpc.module.admin.service.db
 * @ClassName: DbPropertyModifyService
 * @Description: 资源记录接口
 * @Author: rongrong
 * @CreateDate: 2020/8/19
 * @Version: 1.0
 */
public interface DbPropertyModifyService extends IService<DbPropertyModify> {
    /***
     * @description 新增资源记录
     * @param propertyId
     * @return java.lang.Integer
     * @authors rongrong
     * @date 2020/8/19
     * @modified by
     * @version 1.0
     */
 Integer ifPropertyId(String propertyId);
 /***
  * @description 修改资源记录
  * @param propertyModify
  * @return boolean
  * @authors rongrong
  * @date 2020/8/19
  * @modified by
  * @version 1.0
  */
 boolean updateProperty(DbPropertyModify propertyModify);
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
 IPage<DbPropertyList> findByCondition(IPage<DbPropertyList> page, PropertyModifyConditionVo vo);
 /***
  * @description 根据表名获取所有的列名
  * @param proType
  * @return java.util.List<java.lang.String>
  * @authors rongrong
  * @date 2020/8/20
  * @modified by
  * @version 1.0
  */
 List<String> findColumnNames(String proType);
 /***
  * @description 获取志书数据
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
  * @description 获取条目数据
  * @param startDate
  * @param endDate
  * @return java.util.List<com.wanfangdata.cpc.module.admin.model.db.DbLocalChronicleItem>
  * @authors rongrong
  * @date 2020/8/21
  * @modified by
  * @version 1.0
  */
 List<DbLocalChronicleItem> findItemData( String startDate, String endDate);
}
