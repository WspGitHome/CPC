package com.wanfangdata.cpc.module.admin.service.db;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wanfangdata.cpc.module.admin.model.db.LocalChronicle;
import com.wanfangdata.cpc.module.admin.model.db.LocalChronicleItem;

/**
 * @ProjectName: LocalChronicleGrpcSearch
 * @Package: com.wanfangdata.cpc.module.admin.service.db.impl
 * @ClassName: LocalChronicleService
 * @Description: 资源列表接口
 * @Author: rongrong
 * @CreateDate: 2020/8/17
 * @Version: 1.0
 */
public interface PropertyListService extends IService<LocalChronicle> {
    /***
     * @description 根据id查询志书数据
     * @param id
     * @return com.wanfangdata.cpc.module.admin.model.db.LocalChronicle
     * @authors rongrong
     * @date 2020/8/19
     * @modified by
     * @version 1.0
     */
    LocalChronicle selectById(String id);
    /***
     * @description 根据id编辑志书
     * @param id
     * @return com.wanfangdata.cpc.module.admin.model.db.LocalChronicleItem
     * @authors rongrong
     * @date 2020/8/19
     * @modified by
     * @version 1.0
     */
    LocalChronicleItem selectItemById(String id);
    /***
     * @description 根据id查询志书条目
     * @param localChronicle
     * @return boolean
     * @authors rongrong
     * @date 2020/8/19
     * @modified by
     * @version 1.0
     */
    boolean updateLocalChronicle(LocalChronicle localChronicle);
    /***
     * @description 根据id编辑志书条目
     * @param item
     * @return boolean
     * @authors rongrong
     * @date 2020/8/19
     * @modified by
     * @version 1.0
     */
    boolean updateLocalChronicleItem(LocalChronicleItem item);
}
