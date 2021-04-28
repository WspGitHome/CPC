package com.wanfangdata.grpc.server.db.dbentity;

import lombok.Data;

import java.io.Serializable;

/**
 *@program: LocalChronicleGrpcSearch88882
 *@description: 资源类型
 *@author: FLY
 *@create: 2020-08-14 16:44
 */
@Data
public class Library implements Serializable {

    private Integer id;
    private String name;
    private String aliasPath;
}