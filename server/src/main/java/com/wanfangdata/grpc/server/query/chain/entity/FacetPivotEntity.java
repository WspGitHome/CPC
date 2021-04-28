package com.wanfangdata.grpc.server.query.chain.entity;

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
public class FacetPivotEntity implements Serializable {

    public FacetPivotEntity(){}
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
    private String count;

    /**
     * 子节点
     */
    private List<FacetPivotEntity> subdata;

}
