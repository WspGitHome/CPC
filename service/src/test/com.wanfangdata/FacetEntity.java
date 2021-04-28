package com.wanfangdata;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author FLY
 * @date 2019-11-1
 *
 * */
@Data
public class FacetEntity implements Serializable {

    public FacetEntity(){}
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
    private Long count;

    /**
     * 子节点
     */
    private List<FacetEntity> subdata;


}
