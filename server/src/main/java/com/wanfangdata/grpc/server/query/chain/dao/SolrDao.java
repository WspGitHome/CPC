package com.wanfangdata.grpc.server.query.chain.dao;


import com.wanfangdata.grpc.server.query.chain.SolrRequest;
import com.wanfangdata.grpc.server.query.chain.SolrResponse;

/**
 *
 * @author FLY
 * @date 2019-11-1
 *
 * */
public interface SolrDao {
    /**
     * @param solrRequest 处理前的检索参数
     * @exception
     * @return  封装后的检索结构
     * */
    SolrResponse search(SolrRequest solrRequest) throws Exception;
    /**
     * 缓存检索结构
     * @param solrRequest 处理前的检索参数
     * @exception
     * @return  封装后的检索结构
     * */
    SolrResponse searchWithCache(SolrRequest solrRequest,String cacheKey) throws Exception;

}
