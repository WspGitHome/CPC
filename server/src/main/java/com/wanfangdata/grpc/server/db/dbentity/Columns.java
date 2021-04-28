package com.wanfangdata.grpc.server.db.dbentity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *@program: LocalChronicleGrpcSearch88882
 *@description: 栏目树
 *@author: FLY
 *@create: 2020-08-14 16:17
 */
@Data
public class Columns  implements Serializable {


    List<Property> propertyMap;
    Map<String,Column> columnMap;

}