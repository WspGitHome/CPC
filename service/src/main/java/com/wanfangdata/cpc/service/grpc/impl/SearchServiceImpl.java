package com.wanfangdata.cpc.service.grpc.impl;


import com.wanfangdata.cpc.exception.SearchException;
import com.wanfangdata.cpc.model.*;
import com.wanfangdata.cpc.search.utils.GrpcSearchUtil;
import com.wanfangdata.cpc.search.utils.ToGrpcModelUtil;
import com.wanfangdata.cpc.service.SearchService;
import com.wanfangdata.search.*;
import io.grpc.Channel;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
@CacheConfig(cacheNames = {"facetsCache"})
public class SearchServiceImpl implements SearchService {

    private static Logger logger = LoggerFactory.getLogger(SearchServiceImpl.class);

    @GrpcClient("search-grpc-server")
    private Channel searchChannel;

    @Override
    public Map<String, String>  searchOne(String id,String collection,List<String> returnedField)  throws SearchException {
        SearchGrpc.SearchBlockingStub stub = SearchGrpc.newBlockingStub(searchChannel);

        DetailRequest.Builder builder = DetailRequest.newBuilder()
                .setId(id)
                .setCollection(collection);
        if(returnedField!=null){
            builder.addAllReturnedFields(returnedField);
        }
        Map<String, String> result = null;
        try {
            Document document = stub.get(builder.build());
            Map<String, Value> resultMap = document.getFieldsMap();
            result= GrpcSearchUtil.resultToMap(resultMap,0);
            return result;
        }catch (Exception e){
            throw new SearchException("获取详细信息异常，请检查检索服务",collection,e);
        }
    }

    @Override
    public SearchResultViewModel search(SearchViewModel model) throws SearchException {
        SearchGrpc.SearchBlockingStub stub = SearchGrpc.newBlockingStub(searchChannel);
        SearchRequest request= ToGrpcModelUtil.convertSearch(model);
        List<Map<String, String>> result = null;
        try {
            SearchResponse response = stub.query(request);
            SearchResultViewModel resultViewModel= GrpcSearchUtil.searchResultToModel(response,model.getAlias());
            resultViewModel.setPageSize(model.getPageSize());
            resultViewModel.setCurPage(model.getPageNo());
            resultViewModel.setTotalPage((int)Math.ceil(response.getNumFound()/model.getPageSize())+1);
            resultViewModel.setTotalCount((int)response.getNumFound());
            return resultViewModel;
        }catch (Exception e){
            throw new SearchException(model.toString(),model.getCollection(),e);
        }
    }

    @Override
    @Cacheable(value="facetsCache",key = "#cacheKey")
    public SearchResultViewModel searchCache(SearchViewModel model,String cacheKey) throws SearchException {
        SearchResultViewModel result=search(model);
        int hit=result.getTotalCount();
        if(hit>model.getPageSize()){
            int temp=hit-model.getPageSize();
            Random rand = new Random();
            int indexStart = rand.nextInt(temp);
            model.setPageNo(indexStart/model.getPageSize());
        }
        return search(model);
    }
    @Override
    public FacetResultViewModel facet(FacetViewModel model) throws SearchException {
        SearchGrpc.SearchBlockingStub stub = SearchGrpc.newBlockingStub(searchChannel);
        FacetRequest request= ToGrpcModelUtil.convertFacet(model);
        List<Map<String, String>> result = null;
        try {
            FacetResponse response = stub.facet(request);
            FacetResultViewModel resultViewModel= GrpcSearchUtil.facetResultToModel(response,model.getAlias());
            return resultViewModel;
        }catch (Exception e){
            throw new SearchException("聚类检索出错："+model.toString(),model.getCollection(),e);
        }
    }

    @Override
    public FacetResultViewModel facetPivot(FacetPivotViewModel model) throws SearchException {
        SearchGrpc.SearchBlockingStub stub = SearchGrpc.newBlockingStub(searchChannel);
        FacetRequest request= ToGrpcModelUtil.convertFacetPivot(model);
        List<Map<String, String>> result = null;
        try {
            FacetResponse response = stub.facet(request);
            FacetResultViewModel resultViewModel= GrpcSearchUtil.facetResultToModel(response,model.getAlias());
            return resultViewModel;
        }catch (Exception e){
            throw new SearchException("二维聚类检索出错："+model.toString(),model.getCollection(),e);
        }
    }
}
