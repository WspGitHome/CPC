package com.wanfangdata.cpc.module.admin.model.db;

import lombok.Data;

import java.io.Serializable;

/**
 *@program: LocalChronicleGrpcSearch88882
 *@description: 分类专辑和ID映射
 *@author: FLY
 *@create: 2020-08-10 10:19
 */
@Data
public class DbIdForCategory  implements Serializable {

    private String ID;
    private String LibraryId;
    private String ColumnId;

}