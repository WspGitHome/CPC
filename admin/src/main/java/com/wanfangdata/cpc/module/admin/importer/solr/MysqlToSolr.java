package com.wanfangdata.cpc.module.admin.importer.solr;

import com.wanfangdata.cpc.module.admin.importer.TotalModel;
import com.wanfangdata.cpc.module.admin.importer.Watcher;
import com.wanfangdata.cpc.module.admin.model.db.DbPropertyImport;
import com.wanfangdata.cpc.module.admin.service.db.PropertyService;

import java.util.List;
import java.util.Map;

/**
 *@program: LocalChronicleGrpcSearch88882
 *@description: 读取access
 *@author: FLY
 *@create: 2020-08-06 08:54
 */
public class MysqlToSolr {

    private static Integer pageSize=10000;

    public static void importer(DbPropertyImport dbPropertyImport, PropertyService propertyService, Watcher watcher) throws Exception{
        String tableName=dbPropertyImport.getPropertyTableName();
        Integer batchId=dbPropertyImport.getId();
        Integer count=propertyService.selectCount(tableName,batchId);
        TotalModel totalModel =new TotalModel();
        totalModel.setTotal(count);
        watcher.setDataModel(totalModel);
        for(int i=0;i<count ;i=i+pageSize){
            List<Map<String,Object>> list=propertyService.findByPage(tableName,i,pageSize,batchId);
            watcher.process(list);
        }
    }

}