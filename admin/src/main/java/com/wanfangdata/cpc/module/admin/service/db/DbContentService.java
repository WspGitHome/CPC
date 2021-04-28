package com.wanfangdata.cpc.module.admin.service.db;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wanfangdata.cpc.module.admin.model.db.DbContent;
import com.wanfangdata.cpc.module.admin.vo.db.ContentConditionVo;

import java.util.List;


/**
 * @author Linzhaoguan
 * @version V1.0
 * @date 2019年9月11日
 */
public interface DbContentService extends IService<DbContent> {

    /**
     * 分页查询，关联查询文章标签、文章类型
     *
     * @param page
     * @param vo
     * @return
     */
    IPage<DbContent> findByCondition(IPage<DbContent> page, ContentConditionVo vo);

    /**
     * 批量删除文章
     *
     * @param ids
     * @return
     */
    int deleteBatch(Integer[] ids);

    Integer selectCountByColumnId(Integer columnId);

}
