package com.wanfangdata.cpc.module.admin.service.db.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanfangdata.cpc.module.admin.mapper.db.DbContentMapper;
import com.wanfangdata.cpc.module.admin.model.db.DbContent;
import com.wanfangdata.cpc.module.admin.service.db.DbContentService;
import com.wanfangdata.cpc.module.admin.vo.db.ContentConditionVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author Linzhaoguan
 * @version V1.0
 * @date 2019年9月11日
 */
@Service
@AllArgsConstructor
public class DbContentServiceImpl extends ServiceImpl<DbContentMapper, DbContent> implements DbContentService {

    private final DbContentMapper dbContentMapper;


    @Override
    public IPage<DbContent> findByCondition(IPage<DbContent> page, ContentConditionVo vo) {
        return dbContentMapper.findByCondition(page, vo);
    }

    @Override
    public int deleteBatch(Integer[] ids) {
        return dbContentMapper.deleteBatch(ids);
    }

    @Override
    public Integer selectCountByColumnId(Integer columnId) {
        return dbContentMapper.selectCount(Wrappers.<DbContent>lambdaQuery().eq(DbContent::getColumnId, columnId));
    }
}
