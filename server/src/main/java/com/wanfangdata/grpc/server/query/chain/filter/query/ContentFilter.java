package com.wanfangdata.grpc.server.query.chain.filter.query;


import com.wanfangdata.api.chain.Filter;
import com.wanfangdata.api.chain.FilterException;
import com.wanfangdata.grpc.server.config.Config;
import com.wanfangdata.grpc.server.query.chain.util.Constant;
import com.wanfangdata.grpc.server.query.chain.SolrRequest;
import com.wanfangdata.grpc.server.util.SpringContextUtil;
import org.apache.solr.client.solrj.SolrQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * @author FLY
 * @date 2019-11-1
 */
public class ContentFilter implements Filter<SolrRequest,SolrQuery> {

    private static final List<String> RETURNFEILD=new ArrayList<>();

    static {
        RETURNFEILD.add(Constant.ID);
        RETURNFEILD.add(Constant.Title);
        RETURNFEILD.add(Constant.BookID);
        RETURNFEILD.add(Constant.BookTitle);
        RETURNFEILD.add(Constant.RealLevel);
        RETURNFEILD.add(Constant.ParentID);
        RETURNFEILD.add(Constant.NO);
        RETURNFEILD.add(Constant.ColumnId);
    }

    @Override
    public void doFilter(SolrRequest request, SolrQuery params) throws FilterException{
        Config config = (Config) SpringContextUtil.getBean("config");
        List<String> fq=request.getFq();
        if(fq==null||fq.size()<1){
            throw new FilterException("fq",FilterException.Type.NULL);
        }
        if(fq != null && fq.size()>0){
            params.addFilterQuery(fq.toArray(new String[fq.size()]));
        }
        params.setParam("fl",String.join(",",RETURNFEILD));
        params.setQuery(" RealLevel:[* TO 3]");
        params.setRows(3000);
        request.setCore(config.getCoreMap().get(request.getCore()));

    }
}
