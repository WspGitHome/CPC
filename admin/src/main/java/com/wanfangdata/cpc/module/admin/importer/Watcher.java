package com.wanfangdata.cpc.module.admin.importer;

import java.util.List;
import java.util.Map;

/**
 *@program: LocalChronicleGrpcSearch88882
 *@description: 记录出错情况并通知到前台
 *@author: FLY
 *@create: 2020-08-06 08:58
 */
public interface Watcher {

    /**
     * 记录总数
     * */
    void setDataModel(TotalModel data);
    /**
     * 记录错误
     * */
    void recodeError(String error);
    /**
     * 通知前台
     * */
    void notifier();
    /**
     * 插入处理，转化处理等批量操作
     * */
    void process(List<Map<String,Object>> values)  throws ImportException;

}