package com.wanfangdata.grpc.server.query.chain.dao.impl;


import com.wanfangdata.grpc.server.mainten.MaintenFilterChain;
import com.wanfangdata.grpc.server.query.chain.SolrRequest;
import com.wanfangdata.grpc.server.query.chain.SolrResponse;
import com.wanfangdata.grpc.server.query.chain.dao.SolrDao;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 *
 * @author FLY
 * @date 2019-11-1
 *
 * */
@Service
@CacheConfig(cacheNames = {"facetsCache"})
public class SolrDaoImpl implements SolrDao {

    @Autowired
    private SolrClient solrClient;
    @Autowired
    @Qualifier("preMaintenFilterChain")
    MaintenFilterChain preMaintenFilterChain;
    @Autowired
    @Qualifier("subMaintenFilterChain")
    MaintenFilterChain subMaintenFilterChain;

    @Override
    public SolrResponse search(SolrRequest solrRequest) throws Exception {

        return searchSolr(solrRequest);
    }

    /**
     *
     * 增加聚类缓存机制
     *
     * */
    @Override
    @Cacheable(value="facetsCache",key = "#cacheKey")
    public SolrResponse searchWithCache(SolrRequest solrRequest,String cacheKey) throws Exception {
        return searchSolr(solrRequest);
    }


    public SolrResponse searchSolr(SolrRequest solrRequest) throws Exception {
        SolrQuery params = new SolrQuery();
        preMaintenFilterChain.getMockFilterChain(solrRequest.getSearchType()).doFilter(solrRequest,params);
        QueryResponse response = solrClient.query(solrRequest.getCore(),params);
        SolrResponse solrResponse=new SolrResponse(solrRequest.getCore());
        subMaintenFilterChain.getMockFilterChain(solrRequest.getSearchType()).doFilter(response,solrResponse);
        return solrResponse;
    }
}
