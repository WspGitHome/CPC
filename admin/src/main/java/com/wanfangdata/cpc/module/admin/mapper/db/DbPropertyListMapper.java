package com.wanfangdata.cpc.module.admin.mapper.db;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wanfangdata.cpc.module.admin.model.db.LocalChronicle;
import com.wanfangdata.cpc.module.admin.model.db.LocalChronicleItem;
import org.apache.ibatis.annotations.Param;

/**
 * @ProjectName: LocalChronicleGrpcSearch
 * @Package: com.wanfangdata.cpc.module.admin.mapper.db
 * @ClassName: DbLocalChronicleMapper
 * @Description: 描述类
 * @Author: rongrong
 * @CreateDate: 2020/8/17
 * @Version: 1.0
 */
public interface DbPropertyListMapper extends BaseMapper<LocalChronicle> {
    /*** 
     * @description 查询志书
     * @param id
     * @return com.wanfangdata.cpc.module.admin.model.db.LocalChronicle
     * @authors rongrong
     * @date 2020/8/19
     * @modified by
     * @version 1.0
     */
    LocalChronicle selectById(String id);
    /***
     * @description 编辑志书
     * @param localChronicle
     * @return boolean
     * @authors rongrong
     * @date 2020/8/19
     * @modified by
     * @version 1.0
     */
    boolean updateLocalChronicle(@Param("localChronicle") LocalChronicle localChronicle);
    /***
     * @description 查询志书条目
     * @param id
     * @return com.wanfangdata.cpc.module.admin.model.db.LocalChronicleItem
     * @authors rongrong
     * @date 2020/8/19
     * @modified by
     * @version 1.0
     */
    LocalChronicleItem selectItemById(String id);
    /***
     * @description 编辑志书条目
     * @param item
     * @return boolean
     * @authors rongrong
     * @date 2020/8/19
     * @modified by
     * @version 1.0
     */
    boolean updateLocalChronicleItem(@Param("item") LocalChronicleItem item);
}
