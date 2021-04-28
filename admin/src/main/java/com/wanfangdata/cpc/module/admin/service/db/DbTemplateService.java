package com.wanfangdata.cpc.module.admin.service.db;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wanfangdata.cpc.module.admin.model.db.DbTemplate;

import java.io.Serializable;
import java.util.List;

/**
 * @Packagename com.wanfangdata.cpc.module.admin.service.db
 * @Classname DbTemplateService
 * @Description
 * @Authors Mr.Wu
 * @Date 2020/08/09 13:53
 * @Version 1.0
 */
public interface DbTemplateService extends IService<DbTemplate> {


    default boolean removedById(Serializable id) throws Exception {
        return false;
    }

    default boolean toSaveOrUpdate(DbTemplate entity) throws Exception {
        return false;
    }

    IPage<DbTemplate> findByCondition(DbTemplate dbTemplate, Integer pageNumber, Integer pageSize);

    List<Integer> getDBIdsInTemplate();

    default boolean removeDbPath(Integer dbId) throws Exception {
        return false;
    }

    @Override
    DbTemplate getById(Serializable id);

}
