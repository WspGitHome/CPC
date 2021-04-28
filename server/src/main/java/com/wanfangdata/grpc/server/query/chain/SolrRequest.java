package com.wanfangdata.grpc.server.query.chain;


import com.wanfangdata.api.chain.Request;
import lombok.Data;
import org.apache.solr.client.solrj.SolrQuery;

import java.util.List;
import java.util.Map;

/**
 *
 * @author FLY
 * @date 2019-11-1
 *
 * */
@Data
public class SolrRequest implements Request {

    /**
     * 检索关键词
     */
    private String query;
    /**
     * 过滤检索关键词
     */
    private List<String> fq;
    /**
     * 页码
     */
    private int pageNo=1;

    /**
     * 每页数量
     */
    private int pageSize= 10;
    /**
     * 检索库地址
     */
    private String core;

    /**
     * 排序
     */
    private Map<String,String> sort;

    /**
     * 需要一维聚类的属性
     */
    private  List<String> facets;
    /**
     * 需要多维聚类的属性
     */
    private  List<String> facetPivot;
    /**
     * 返回参数定义
     */
    private  List<String> returnFeild;
    /**
     * 高亮字段
     */
    private  List<String> highLight;
    /**
     * 检索类型
     */
    private String searchType;

}
