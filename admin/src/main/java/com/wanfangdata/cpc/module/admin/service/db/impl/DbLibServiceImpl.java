package com.wanfangdata.cpc.module.admin.service.db.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanfangdata.cpc.module.admin.mapper.db.*;
import com.wanfangdata.cpc.module.admin.model.db.DbLibrary;
import com.wanfangdata.cpc.module.admin.model.db.DbLibtype;
import com.wanfangdata.cpc.module.admin.service.db.DbLibService;
import com.wanfangdata.cpc.module.admin.service.db.DbTemplateService;
import com.wanfangdata.cpc.module.admin.vo.db.DblibraryConditionVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author Linzhaoguan
 * @version V1.0
 * @date 2019年9月11日
 */
@Service
@AllArgsConstructor
public class DbLibServiceImpl extends ServiceImpl<DbLibraryMapper, DbLibrary> implements DbLibService {


    private final DbLibraryMapper dbLibraryMapper;
    private final DbTemplateService dbTemplateService;
    private final DbLibtypeMapper dbLibtypeMapper;
    private final DbColumnMapper dbColumnMapper;
    private final DbContentMapper dbContentMapper;
    private final DbKeywordMapper dbKeywordMapper;

    @Override
    public List<DbLibtype> selecLibtypes() {
        return dbLibtypeMapper.selecLibtypes();
    }

    @Override
    public DbLibrary getOne(Wrapper<DbLibrary> queryWrapper, boolean throwEx) {
        return super.getOne(queryWrapper, throwEx);
    }

    @Override
    public List<DbLibrary> selectDbName() {
        return dbLibraryMapper.selectDbName();
    }

    @Override
    public IPage<DbLibrary> findByCondition(IPage<DbLibrary> page, DblibraryConditionVo vo) {
        return dbLibraryMapper.findByCondition(page, vo);
    }

    @Override
    public List<DbLibrary> findAll(Integer type) {
        return dbLibraryMapper.findAll(type);
    }

    @Override
    public boolean updateById(DbLibrary entity) {
        return super.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBatch(Integer id) throws Exception {
        dbTemplateService.removedById(id);
        dbColumnMapper.deleteByLibId(id);
        dbContentMapper.deleteByLibId(id);
        dbKeywordMapper.deleteByLibId(id);
        return  dbLibraryMapper.deleteById(id);
    }
}
