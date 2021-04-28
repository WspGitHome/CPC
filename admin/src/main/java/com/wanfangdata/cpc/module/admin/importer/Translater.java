package com.wanfangdata.cpc.module.admin.importer;


import java.util.Map;

/**
 *@program: LocalChronicleGrpcSearch88882
 *@description: 将标引转为代码
 *@author: FLY
 *@create: 2020-08-07 10:22
 */
public interface Translater {

    void translater(Map<String, Object> value,Integer libraryId) throws ImportException;
}