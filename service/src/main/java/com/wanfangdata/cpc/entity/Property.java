package com.wanfangdata.cpc.entity;

import lombok.Data;

import java.io.Serializable;

/**
 *@program: LocalChronicleGrpcSearch88882
 *@description: 资源类型
 *@author: FLY
 *@create: 2020-08-14 16:44
 */
@Data
public class Property  implements Serializable {

    private Integer id;
    private String propertyName;
    private String propertyTableName;
    private String propertySolrAlias;
}