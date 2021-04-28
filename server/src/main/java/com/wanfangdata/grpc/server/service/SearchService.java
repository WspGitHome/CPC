package com.wanfangdata.grpc.server.service;

import com.wanfangdata.grpc.*;
import com.wanfangdata.grpc.server.query.chain.SolrRequest;
import com.wanfangdata.grpc.server.query.chain.SolrResponse;
import com.wanfangdata.grpc.server.util.*;
import com.wanfangdata.grpc.server.query.chain.dao.SolrDao;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


@GrpcService
public class SearchService extends SearchServiceGrpc.SearchServiceImplBase {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SolrDao solrDao;

    @Override
    public void getSearch(SearchRequest request, StreamObserver<SearchResponse> responseObserver) {
        try{
            SolrRequest solrRequest=ConvertUtil.convetRequest(request);
            logger.info("SearchRequest:{}",solrRequest.toString());
            SolrResponse solrResponse=null;
            if("".equals(solrRequest.getQuery())){
                //缓存 同库同字段聚类数量不变，该功能适用于导航聚类
                solrResponse=solrDao.searchWithCache(solrRequest,getCacheKey(solrRequest));
            }else{
                solrResponse=solrDao.search(solrRequest);
            }
            logger.info("SearchResponse hit:{}",solrResponse.getHit());
            SearchResponse searchResponse=ConvertUtil.convetResponse(solrResponse);
            responseObserver.onNext(searchResponse);
        }catch (Exception e){
            e.printStackTrace();
        }
        responseObserver.onCompleted();
    }

    private String getCacheKey(SolrRequest solrRequest){
        String fq=String.join(":",solrRequest.getFq());
        String facets=String.join(":",solrRequest.getFacets());
        String facetPivot=String.join(":",solrRequest.getFacetPivot());
        String sort=String.join(":",solrRequest.getSort().keySet())+String.join(":",solrRequest.getSort().keySet());
        String cacheKey=String.join("#",fq,facets,facetPivot,sort,solrRequest.getCore(),String.valueOf(solrRequest.getPageNo()),String.valueOf(solrRequest.getPageSize()));
        return cacheKey;
    }
}