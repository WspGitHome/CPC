package com.wanfangdata.grpc.server.util;

import com.alibaba.fastjson.JSON;
import com.wanfangdata.grpc.*;
import com.wanfangdata.grpc.server.config.Config;
import com.wanfangdata.grpc.server.query.chain.SolrRequest;
import com.wanfangdata.grpc.server.query.chain.SolrResponse;
import com.wanfangdata.grpc.server.query.chain.entity.FacetEntity;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.*;


public class ConvertUtil {


    public static SolrRequest convetRequest(SearchRequest request) {
        Config config = (Config) SpringContextUtil.getBean("config");
        SolrRequest solrRequest = new SolrRequest();
        solrRequest.setCore(request.getSearcher().getCore());
        solrRequest.setQuery(request.getSearcher().getQuery());
        solrRequest.setFq(request.getSearcher().getFqList());
        solrRequest.setPageSize(request.getSearcher().getPageSize());
        solrRequest.setPageNo(request.getSearcher().getPageNo());
        solrRequest.setFacets(request.getSearcher().getFacetsList());
        if (request.getSearcher().getFacetPivotList().contains("CategoryA") ||
                request.getSearcher().getFacetPivotList().contains("CategoryB")) {
            List<String> facets = new ArrayList<>();
            facets.add("ColumnId");
            solrRequest.setFacets(facets);
            solrRequest.setFacetPivot(new ArrayList<>());

        } else {
            solrRequest.setFacetPivot(request.getSearcher().getFacetPivotList());
        }
        solrRequest.setSearchType(request.getSearcher().getSearchType());
        solrRequest.setHighLight(request.getSearcher().getHighLightList());
        solrRequest.setReturnFeild(request.getSearcher().getReturnFeildList());
        if (request.getSearcher().getSortsList() != null) {
            Map<String, String> map = new HashMap<>();
            for (Sort sort : request.getSearcher().getSortsList()) {
                String feild = sort.getFeild();
                String order = sort.getOrder();
                map.put(feild, order);
            }
            solrRequest.setSort(map);
        }
        return solrRequest;
    }

    public static SearchResponse convetResponse(SolrResponse response) {
        String facets = JSON.toJSONString(response.getFacets());
        String facetPivot = JSON.toJSONString(response.getFacetPivot());
        if (null != response.getFacets() && response.getFacets().keySet().size() == 1 && response.getFacets().containsKey("ColumnId")) {
            facetPivot = JSON.toJSONString(response.getFacets().get("ColumnId"));
            facets = "";
        }
        Resulter resulter = Resulter.newBuilder()
                .setResults(JSON.toJSONString(response.getSearchResults()))
                .setFacets(facets)
                .setFacetPivot(facetPivot)
                .setExtraData(JSON.toJSONString(response.getExtraData()))
                .setHit(response.getHit())
                .setCurPage(response.getCurPage())
                .setTotalPage(response.getTotalPage())
                .setPageSize(response.getPageSize()).build();
        return SearchResponse.newBuilder()
                .setResulter(resulter).build();
    }
}