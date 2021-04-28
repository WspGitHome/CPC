package com.wanfangdata.grpc.server.query.chain.filter.result;


import com.wanfangdata.api.chain.Filter;
import com.wanfangdata.api.chain.FilterException;
import com.wanfangdata.grpc.server.query.chain.SolrResponse;
import com.wanfangdata.grpc.server.query.chain.util.Catalog;
import com.wanfangdata.grpc.server.query.chain.util.ComparatorItem;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

import java.util.Collections;
import java.util.Map;


/**
 * @author FLY
 * @date 2019-11-1
 */
public class ContentSearchFilter implements Filter<QueryResponse,SolrResponse> {

    @Override
    public void doFilter(QueryResponse queryResponse, SolrResponse solrResponse) throws FilterException {
        ComparatorItem comparatorItem=new ComparatorItem();
        if(solrResponse.getSearchResults()!=null) {
            Collections.sort(solrResponse.getSearchResults(), comparatorItem);
        }
    }
}
