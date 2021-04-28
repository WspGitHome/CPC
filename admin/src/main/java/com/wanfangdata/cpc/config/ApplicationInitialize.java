package com.wanfangdata.cpc.config;

import com.wanfangdata.cpc.module.admin.model.db.DbLibrary;
import com.wanfangdata.cpc.module.admin.model.db.DbLibtype;
import com.wanfangdata.cpc.module.admin.model.db.DbProperty;
import com.wanfangdata.cpc.module.admin.service.db.DbLibService;
import com.wanfangdata.cpc.module.admin.service.db.PropertyService;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 *
 * 应用配置初始化
 * @author fly
 * */
@Component
@Data
public class ApplicationInitialize {


    List<DbLibtype> libraries;
    List<DbLibrary> dbNames;
    List<DbProperty> dbProperties;
    private final DbLibService dbLibService;
    private final PropertyService propertyService;
    @PostConstruct
    private void init(){
        libraries=dbLibService.selecLibtypes();
        dbNames=dbLibService.selectDbName();
        dbProperties = propertyService.selectProperty();
    }
}
