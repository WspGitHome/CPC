package com.wanfangdata.cpc.module.admin.importer;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *@program: LocalChronicleGrpcSearch88882
 *@description: 数据导入异常
 *@author: FLY
 *@create: 2020-08-12 11:06
 */
@Data
@AllArgsConstructor
public class ImportException extends Exception {

    private String id;
    private String msg;
    private Exception exceptionHolder;

}