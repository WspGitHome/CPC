package com.wanfangdata.grpc.server.query.chain;


import com.wanfangdata.api.chain.Response;
import com.wanfangdata.grpc.server.query.chain.entity.FacetEntity;
import com.wanfangdata.grpc.server.query.chain.entity.FacetPivotEntity;
import lombok.Data;

import java.util.List;
import java.util.Map;


/**
 *
 * @author FLY
 * @date 2019-11-1
 *
 * */
@Data
public class SolrResponse implements Response {
    /**
     * 查询结果
     */
    List<Map<String,String>> searchResults;

    /**
     * 返回命中数
     */
    int hit = 0;
    /**
     * 当前页数
     */
    int curPage = 0;
    /**
     * 总页数
     */
    int pageSize = 0;
    /**
     * 总页数
     */
    int totalPage = 0;
    /**
     * 检索库地址
     */
    private String core;
    /**
     * 返回聚类列表
     */
    Map<String,List<FacetEntity>> facets;
    /**
     * 返回多维聚类列表
     */
    List<FacetPivotEntity> facetPivot;
    /**
     * 拓展返回值
     */
    Object extraData;

    public SolrResponse(String core){
        this.core=core;
    }
}
