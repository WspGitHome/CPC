package com.wanfangdata.cpc.model;

import lombok.Data;

import java.util.List;

/**
 * @author FLY
 * @create
 **/
@Data
public class FacetModel {

    /**
     * 所属字段
     */
    private String field;
    /**
     * 字段值
     */
    private String value;
    /**
     * 命中数
     */
    private Integer count;
    /**
     * 所属字段
     */
    private String parent;

    /**
     * 子节点
     */
    private List<FacetModel> subdata;

}
