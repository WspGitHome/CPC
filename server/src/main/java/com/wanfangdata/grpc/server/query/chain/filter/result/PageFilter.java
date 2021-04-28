package com.wanfangdata.grpc.server.query.chain.filter.result;


import com.wanfangdata.api.chain.Filter;
import com.wanfangdata.api.chain.FilterException;
import com.wanfangdata.grpc.server.query.chain.SolrResponse;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.util.SimpleOrderedMap;


/**
 * @author FLY
 * @date 2019-11-1
 */
public class PageFilter implements Filter<QueryResponse,SolrResponse> {

    @Override
    public void doFilter(QueryResponse queryResponse, SolrResponse solrResponse) throws FilterException {
        int hit = (int) queryResponse.getResults().getNumFound();
        SimpleOrderedMap header = (SimpleOrderedMap) queryResponse.getResponseHeader().get("params");
        int rows = Integer.parseInt((String) header.get("rows"));
        int start = (int) queryResponse.getResults().getStart();
        solrResponse.setHit(hit);
        solrResponse.setPageSize(rows);
        solrResponse.setCurPage((start + rows) / rows);
        int remainder = hit % rows;
        int consult = hit / rows;
        solrResponse.setTotalPage((remainder == 0) ? consult : (consult + 1));
    }
}
