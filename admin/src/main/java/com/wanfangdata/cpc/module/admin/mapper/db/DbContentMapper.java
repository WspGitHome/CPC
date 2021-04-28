package com.wanfangdata.cpc.module.admin.mapper.db;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wanfangdata.cpc.module.admin.model.db.DbContent;
import com.wanfangdata.cpc.module.admin.vo.db.ContentConditionVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface DbContentMapper extends BaseMapper<DbContent> {

    /**
     * 分页查询，关联查询文章标签、文章类型
     *
     * @param page
     * @param vo
     * @return
     */
    IPage<DbContent> findByCondition(@Param("page") IPage<DbContent> page, @Param("vo") ContentConditionVo vo);

    /**
     * 批量删除文章
     *
     * @param ids
     * @return
     */
    int deleteBatch(Integer[] ids);

    int deleteByLibId(Integer libId);

    int deleteByColumnId(Integer columnId);
}
