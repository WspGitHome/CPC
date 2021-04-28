package com.wanfangdata.cpc.module.admin.service.db.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanfangdata.cpc.module.admin.mapper.db.DbColumnMapper;
import com.wanfangdata.cpc.module.admin.mapper.db.DbKeywordMapper;
import com.wanfangdata.cpc.module.admin.model.db.DbColumn;
import com.wanfangdata.cpc.module.admin.model.db.DbKeyword;
import com.wanfangdata.cpc.module.admin.service.db.DbKeywordService;
import com.wanfangdata.cpc.module.admin.vo.db.ColumnConditionVo;
import com.wanfangdata.cpc.module.admin.vo.db.KeywordConditionVo;
import lombok.AllArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @ProjectName: LocalChronicleGrpcSearch
 * @Package: com.wanfangdata.cpc.module.admin.service.db.impl
 * @ClassName: DbKeywordServiceImpl
 * @Description: 描述类
 * @Author: rongrong
 * @CreateDate: 2020/8/3
 * @Version: 1.0
 */
@Service
@AllArgsConstructor
public class DbKeywordServiceImpl extends ServiceImpl<DbKeywordMapper, DbKeyword> implements DbKeywordService {
    private final DbKeywordMapper dbKeywordMapper;
    private final DbColumnMapper dbColumnMapper;

    @Override
    public IPage<DbKeyword> findByCondition(IPage<DbKeyword> page, KeywordConditionVo vo) {
        ColumnConditionVo conditionVo = new ColumnConditionVo();
        conditionVo.setId(vo.getColumnId());
        conditionVo.setPid(0);
        Integer[] coulumnIds = new Integer[]{vo.getColumnId()};
        List<Integer> columnIdByCondition = dbColumnMapper.getColumnIdByCondition(conditionVo);
        if (!CollectionUtils.isEmpty(columnIdByCondition)) {
            conditionVo.setPid(vo.getColumnId());
            conditionVo.setId(null);
            coulumnIds = dbColumnMapper.getColumnIdByCondition(conditionVo).toArray(coulumnIds);
        }
        vo.setColumnIds(coulumnIds);
        return dbKeywordMapper.findByCondition(page, vo);
    }

    @Override
    public int deleteBatch(Integer[] ids) {
        return dbKeywordMapper.deleteBatch(ids);
    }

    @Override
    public int insertKeyword(DbKeyword keyword) {
        return dbKeywordMapper.insertKeyword(keyword);
    }

    @Override
    public DbKeyword moveUp(Integer orderNum) {
        return dbKeywordMapper.moveUp(orderNum);
    }

    @Override
    public DbKeyword moveDown(Integer orderNum) {
        return dbKeywordMapper.moveDown(orderNum);
    }

    @Override
    public Integer selectCountByColumnId(Integer columnId) {
        return dbKeywordMapper.selectCount(Wrappers.<DbKeyword>lambdaQuery().eq(DbKeyword::getColumnId, columnId));
    }

}

