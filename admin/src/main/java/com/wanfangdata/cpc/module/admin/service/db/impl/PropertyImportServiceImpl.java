package com.wanfangdata.cpc.module.admin.service.db.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanfangdata.cpc.module.admin.mapper.db.DbPropertyImportMapper;
import com.wanfangdata.cpc.module.admin.model.db.DbPropertyImport;
import com.wanfangdata.cpc.module.admin.service.db.PropertyImportService;
import com.wanfangdata.cpc.module.admin.vo.db.PropertyImportConditionVo;
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
public class PropertyImportServiceImpl extends ServiceImpl<DbPropertyImportMapper, DbPropertyImport> implements PropertyImportService {

    DbPropertyImportMapper dbPropertyImportMapper;
    @Override
    public List<DbPropertyImport> findByCondition(IPage<DbPropertyImport> page, PropertyImportConditionVo vo) {
        return dbPropertyImportMapper.findByCondition(page,vo);
    }

    @Override
    public DbPropertyImport getById(Integer id) {
        return dbPropertyImportMapper.getById(id);
    }
}
