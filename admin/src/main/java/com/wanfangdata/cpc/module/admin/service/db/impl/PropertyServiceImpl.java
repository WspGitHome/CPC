package com.wanfangdata.cpc.module.admin.service.db.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanfangdata.cpc.module.admin.mapper.db.DbPropertyMapper;
import com.wanfangdata.cpc.module.admin.model.db.DbIdForCategory;
import com.wanfangdata.cpc.module.admin.model.db.DbProperty;
import com.wanfangdata.cpc.module.admin.model.db.DbPropertyList;
import com.wanfangdata.cpc.module.admin.service.db.PropertyService;
import com.wanfangdata.cpc.module.admin.vo.db.PropertyConditionVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Linzhaoguan
 * @version V1.0
 * @date 2019年9月11日
 */
@Service
@AllArgsConstructor
public class PropertyServiceImpl extends ServiceImpl<DbPropertyMapper, DbProperty> implements PropertyService {

    private final  DbPropertyMapper dbPropertyMapper;

    @Override
    public Integer saveBatch(String table, List<String> feilds, List<List<Object>> values) {
        return dbPropertyMapper.saveBatch(table,feilds,values);
    }
    @Override
    public Integer updataColumn(String table,Map<String,String> feilds) {
        return dbPropertyMapper.updataColumn(table,feilds);
    }

    @Override
    public List<Map<String, Object>> selectSchama(String table) {
        return dbPropertyMapper.selectSchama(table);
    }

    @Override
    public List<Map<String, Object>> findByPage(String table, Integer start, Integer size,Integer batchId) {
        return dbPropertyMapper.findByPage(table,start,size,batchId);
    }

    @Override
    public Integer selectCount(String table,Integer batchId) {
        return dbPropertyMapper.selectCount(table,batchId);
    }

    @Override
    public List<DbIdForCategory> selectIdForCategory(String table, List<String> ids) {
        return dbPropertyMapper.selectIdForCategory(table,ids);
    }

    @Override
    public List<DbProperty> selectProperty() {
        return dbPropertyMapper.selectProperty();
    }

    @Override
    public IPage<DbPropertyList> findByCondition(IPage<DbPropertyList> page, PropertyConditionVo vo) {
        return dbPropertyMapper.findByCondition(page,vo);
    }

    @Override
    public int deletePro(String proId,String proType) {
        return dbPropertyMapper.deletePro(proId,proType);
    }
}
