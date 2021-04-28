package com.wanfangdata.cpc.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *@program: LocalChronicleGrpcSearch88882
 *@description: 栏目
 *@author: FLY
 *@create: 2020-08-14 16:14
 */
@Data
public class Column implements Serializable{

    private Integer id;
    private Integer pid;
    private String name;
    private Integer libraryId;
    private String libraryName;
    private Integer propertyId;
    private String propertySolrAlias;
    private String description;
    private String picture1;
    private String picture2;
    private String sort;
    private String ColumnId;
    private Integer parent;
    private List<Column> children;

}