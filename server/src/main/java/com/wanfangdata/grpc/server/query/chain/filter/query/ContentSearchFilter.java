package com.wanfangdata.grpc.server.query.chain.filter.query;


import com.wanfangdata.api.chain.Filter;
import com.wanfangdata.api.chain.FilterException;
import com.wanfangdata.grpc.server.config.Config;
import com.wanfangdata.grpc.server.query.chain.SolrRequest;
import com.wanfangdata.grpc.server.query.chain.util.Constant;
import com.wanfangdata.grpc.server.util.SpringContextUtil;
import org.apache.solr.client.solrj.SolrQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * @author FLY
 * @date 2019-11-1
 */
public class ContentSearchFilter implements Filter<SolrRequest, SolrQuery> {

    private static final List<String> RETURNFEILD = new ArrayList<>();
    private static final String[] highLights = {"Content", "Title"};
    private static final String HighlightSimplePre = "<em>";
    private static final String HighlightSimplePost = "</em>";
    private static final String Title = "Title";
    private static final String Content = "Content";

    static {
        RETURNFEILD.add(Constant.ID);
        RETURNFEILD.add(Constant.Title);
        RETURNFEILD.add(Constant.Content);
        RETURNFEILD.add(Constant.NO);
        RETURNFEILD.add(Constant.ColumnId);
    }

    @Override
    public void doFilter(SolrRequest request, SolrQuery params) throws FilterException {
        Config config = (Config) SpringContextUtil.getBean("config");
        String q = request.getQuery();
        if (q == null || "".equals(q.trim()) || q.contains(":")) {
            q = "-Id:0";
        } else {
            q = Title + ":" + q + " OR " + Content + ":" + q;
        }

        params.setQuery(q);
        //过滤字段
        List<String> fq = request.getFq();
        if (fq == null || fq.size() < 1) {
            throw new FilterException("fq", FilterException.Type.NULL);
        }
        if (fq != null && fq.size() > 0) {
            params.addFilterQuery(fq.toArray(new String[fq.size()]));
        }
        //返回字段
        params.setParam("fl", String.join(",", RETURNFEILD));
        //排序字段
        params.addSort(new SolrQuery.SortClause(Constant.NO, SolrQuery.ORDER.asc));
        //高亮字段
        params.setHighlight(true);
        params.setHighlightSimplePre(HighlightSimplePre);
        params.setHighlightSimplePost(HighlightSimplePost);
        for (String highLight : highLights) {
            params.addHighlightField(highLight);
        }
        //设置页码
        int pageNum = request.getPageNo();
        int pageSize = request.getPageSize();
        pageSize = (pageSize == 0 ? Constant.pageSize : pageSize); //每页数量默认为1
        pageNum = (pageNum < 1 ? 1 : pageNum) - 1;
        params.setStart(pageNum * pageSize);
        params.setRows(pageSize);
        request.setCore(config.getCoreMap().get(request.getCore()));
    }
}
