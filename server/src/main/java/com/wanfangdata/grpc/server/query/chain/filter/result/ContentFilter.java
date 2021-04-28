package com.wanfangdata.grpc.server.query.chain.filter.result;


import com.wanfangdata.api.chain.Filter;
import com.wanfangdata.api.chain.FilterException;
import com.wanfangdata.grpc.server.query.chain.SolrResponse;
import com.wanfangdata.grpc.server.query.chain.entity.FacetEntity;
import com.wanfangdata.grpc.server.query.chain.util.Catalog;
import com.wanfangdata.grpc.server.query.chain.util.ComparatorItem;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author FLY
 * @date 2019-11-1
 */
public class ContentFilter implements Filter<QueryResponse,SolrResponse> {

    @Override
    public void doFilter(QueryResponse queryResponse, SolrResponse solrResponse) throws FilterException {
        SolrDocumentList solrDocumentList = queryResponse.getResults();
        if (solrDocumentList == null || solrDocumentList.size() < 1) {
            return;
        }
        ComparatorItem comparatorItem=new ComparatorItem();
        Catalog<Map>  catalogTree=comparatorItem.getCatalogTree(solrDocumentList);
        solrResponse.setExtraData(catalogTree);
    }
}
