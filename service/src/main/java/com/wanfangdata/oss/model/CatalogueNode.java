package com.wanfangdata.oss.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *@program: catalogue
 *@description: 目录节点
 *@author: FLY
 *@create: 2020-05-06 10:50
 */
@Data
public class CatalogueNode implements Serializable{

    private String id;

    private String title;

    private String parentID;

    private String bookID;

    private String page;

    private String pageCount;

    private String beginPara;

    private String endPara;

    private String no;

    private String location;

    /**
     * 前台用于条目开始页码（加上了01偏移量）
     * */
    private String startPara;
    /**
     * 前台用于条目结束页码（加上了01偏移量）
     * */
    private String finishPara;

    private List<CatalogueNode> catalogueNodes;

}
