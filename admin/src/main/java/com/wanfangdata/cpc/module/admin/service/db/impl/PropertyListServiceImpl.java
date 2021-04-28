package com.wanfangdata.cpc.module.admin.service.db.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanfangdata.cpc.module.admin.mapper.db.DbPropertyListMapper;
import com.wanfangdata.cpc.module.admin.model.db.LocalChronicle;
import com.wanfangdata.cpc.module.admin.model.db.LocalChronicleItem;
import com.wanfangdata.cpc.module.admin.service.db.PropertyListService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


/**
 * @ProjectName: LocalChronicleGrpcSearch
 * @Package: com.wanfangdata.cpc.module.admin.service.db.impl
 * @ClassName: LocalChronicleServiceImpl
 * @Description: 资源列表接口实现
 * @Author: rongrong
 * @CreateDate: 2020/8/17
 * @Version: 1.0
 */
@Service
@AllArgsConstructor
public  class PropertyListServiceImpl extends ServiceImpl<DbPropertyListMapper, LocalChronicle> implements PropertyListService {
    private final DbPropertyListMapper dbPropertyListMapper;
    @Override
    public LocalChronicle selectById(String id) {
        return dbPropertyListMapper.selectById(id);
    }

    @Override
    public boolean updateLocalChronicle(LocalChronicle localChronicle) {
        return dbPropertyListMapper.updateLocalChronicle(localChronicle);
    }

    @Override
    public LocalChronicleItem selectItemById(String id) {
        return dbPropertyListMapper.selectItemById(id);
    }

    @Override
    public boolean updateLocalChronicleItem(LocalChronicleItem item) {
        return dbPropertyListMapper.updateLocalChronicleItem(item);
    }
}
