package com.wanfangdata.cpc.module.admin.service.db;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wanfangdata.cpc.module.admin.model.db.DbLibrary;
import com.wanfangdata.cpc.module.admin.model.db.DbLibtype;
import com.wanfangdata.cpc.module.admin.model.db.DbTemplateInfo;
import com.wanfangdata.cpc.module.admin.vo.db.DblibraryConditionVo;

import java.util.List;

/**
 * @author Linzhaoguan
 * @version V1.0
 * @date 2019年9月11日
 */
public interface DbLibService extends IService<DbLibrary> {

    List<DbLibtype> selecLibtypes();

    /***
     * @description 查询数据库名称
     * @param
     * @return java.util.List<com.wanfangdata.cpc.module.admin.model.db.DbLibrary>
     * @authors rongrong
     * @date 2020/7/30
     * @modified by
     * @version 1.0
     */
    List<DbLibrary> selectDbName();

    /**
     * 分页查询
     *
     * @param vo
     * @return
     */
    IPage<DbLibrary> findByCondition(IPage<DbLibrary> page, DblibraryConditionVo vo);

    /**
     * 查询所有数据库
     *
     * @param type
     * @return
     */
    List<DbLibrary> findAll(Integer type);

    /**
     * @param id
     * @return boolean
     * @description 删除数据库 及其关联数据信息
     * @authors Mr.Wu
     * @date 2020/08/13
     * @modified by
     * @version 1.0
     **/

    int deleteBatch(Integer id) throws Exception;
}
