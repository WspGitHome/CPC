package com.wanfangdata.cpc.module.admin.service.db.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanfangdata.cpc.module.admin.mapper.db.DbPropertyModifyMapper;
import com.wanfangdata.cpc.module.admin.model.db.DbLocalChronicle;
import com.wanfangdata.cpc.module.admin.model.db.DbLocalChronicleItem;
import com.wanfangdata.cpc.module.admin.model.db.DbPropertyList;
import com.wanfangdata.cpc.module.admin.model.db.DbPropertyModify;
import com.wanfangdata.cpc.module.admin.service.db.DbPropertyModifyService;
import com.wanfangdata.cpc.module.admin.vo.db.PropertyModifyConditionVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @ProjectName: LocalChronicleGrpcSearch
 * @Package: com.wanfangdata.cpc.module.admin.service.db.impl
 * @ClassName: DbPropertyModifyServiceImpl
 * @Description: 资源记录接口实现
 * @Author: rongrong
 * @CreateDate: 2020/8/19
 * @Version: 1.0
 */
@Service
@AllArgsConstructor
public class DbPropertyModifyServiceImpl extends ServiceImpl<DbPropertyModifyMapper, DbPropertyModify> implements DbPropertyModifyService {
    private final DbPropertyModifyMapper propertyModifyMapper;
    @Override
    public Integer ifPropertyId(String propertyId) {
        return propertyModifyMapper.ifPropertyId(propertyId);
    }

    @Override
    public boolean updateProperty(DbPropertyModify propertyModify) {
        return propertyModifyMapper.updateProperty(propertyModify);
    }

    @Override
    public IPage<DbPropertyList> findByCondition(IPage<DbPropertyList> page, PropertyModifyConditionVo vo) {
        return propertyModifyMapper.findByCondition(page,vo);
    }

    @Override
    public List<String> findColumnNames(String proType) {
        return propertyModifyMapper.findColumnNames(proType);
    }

    @Override
    public List<DbLocalChronicle> findLocalChronicleData( String startDate, String endDate) {
        return propertyModifyMapper.findLocalChronicleData(startDate,endDate);
    }

    @Override
    public List<DbLocalChronicleItem> findItemData( String startDate, String endDate) {
        return propertyModifyMapper.findItemData(startDate,endDate);
    }
}
