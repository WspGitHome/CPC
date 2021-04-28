package com.wanfangdata.grpc.server.query.chain.filter.query;


import com.wanfangdata.api.chain.Filter;
import com.wanfangdata.api.chain.FilterException;
import com.wanfangdata.grpc.server.config.Config;
import com.wanfangdata.grpc.server.query.chain.SolrRequest;
import com.wanfangdata.grpc.server.query.chain.util.CategoryData;
import com.wanfangdata.grpc.server.query.chain.util.Constant;
import com.wanfangdata.grpc.server.util.SpringContextUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;

import java.util.*;

/**
 * @author FLY
 * @date 2019-11-1
 */
public class QueryFilter implements Filter<SolrRequest, SolrQuery> {

    private static final String HighlightSimplePre = "<em>";
    private static final String HighlightSimplePost = "</em>";
    private static final String CATEGORY = "Category";
    private static final String Province = "Province";
    private static final String City = "City";
    private static final String County = "County";
    private static final String NOSEARCH_CATEGORYA = "-CategoryA:无";
    private static final String NOSEARCH_CATEGORYB = "-CategoryB:无";


    @Override
    public void doFilter(SolrRequest request, SolrQuery params) throws FilterException {
        String q = request.getQuery();
        Config config = (Config) SpringContextUtil.getBean("config");
        if (q == null || "".equals(q.trim()) || q.contains(":*")) {
            q = "-Id:0";
        } else {
            //设置默认高亮字段
            params.setHighlightSimplePre(HighlightSimplePre);
            params.setHighlightSimplePost(HighlightSimplePost);
            params.setHighlightFragsize(200);
            params.addHighlightField(q.split(":")[0]);
        }
        q = q + " AND LibraryId:" + config.getDbMap().get(request.getCore());
        params.setQuery(q);
        //设置页码
        int pageNum = request.getPageNo();
        int pageSize = request.getPageSize();
        pageSize = (pageSize == 0 ? Constant.pageSize : pageSize); //每页数量默认为1
        pageNum = (pageNum < 1 ? 1 : pageNum) - 1;
        params.setStart(pageNum * pageSize);
        params.setRows(pageSize);

        //过滤语句
        List<String> fq = request.getFq();
        if (fq != null && fq.size() > 0) {
            fq = filterQuery(fq, request);
            params.addFilterQuery(fq.toArray(new String[fq.size()]));
        }
        //过滤标引为无的专辑，因为一些封面等数据作检索
/*        if (CategoryData.getInstance().hasCategoryConfig(request.getCore())) {
            params.addFilterQuery(NOSEARCH_CATEGORYA);
            params.addFilterQuery(NOSEARCH_CATEGORYB);
        }*/
        //聚类
        List<String> facets = request.getFacets();
        if (facets != null && facets.size() > 0) {
            params.setFacet(true);
            params.setFacetLimit(500);
            if (facets.contains(Constant.ColumnId)) {
                params.setParam("f.ColumnId.facet.prefix", config.getDbMap().get(request.getCore()) + Constant.separator_column);
                if(request.getCore().equals("LocalChronicleItemDialect")){
                    params.setParam("f.ColumnId.facet.prefix", config.getDbMap().get(request.getCore()) + Constant.separator_column+"376"+Constant.separator_column);

                }
            }
            params.addFacetField(facets.toArray(new String[facets.size()]));
        }
        //多维聚类
        List<String> facetsPivot = request.getFacetPivot();
        if (facetsPivot != null && facetsPivot.size() > 0) {
            params.setFacet(true);
            String field = "";
            for (int n = 0; n < facetsPivot.size(); n++) {
                field = field.equals("") ? facetsPivot.get(n) : field + "," + facetsPivot.get(n);
            }
            params.set("facet.pivot", field);
        }
        //高亮字段
        List<String> hlight = request.getHighLight();
        if (hlight != null && hlight.size() > 0) {
            params.setHighlight(true);
            params.setHighlightSimplePre(HighlightSimplePre);
            params.setHighlightSimplePost(HighlightSimplePost);
            params.setHighlightFragsize(200);
            for (String highLight : hlight) {
                params.addHighlightField(highLight);
            }
        }
        //排序字段
        Map<String, String> sortMap = request.getSort();
        if (sortMap != null && sortMap.size() > 0) {
            for (Map.Entry<String, String> entry : sortMap.entrySet()) {
                SolrQuery.SortClause sortClause = new SolrQuery.SortClause(entry.getKey(), entry.getValue());
                params.addSort(sortClause);
            }
        }
        //返回字段
        if (request.getReturnFeild() != null) {
            params.setParam("fl", String.join(",", request.getReturnFeild()));
        }
        request.setCore(config.getCoreMap().get(request.getCore()));

    }

    private List<String> filterQuery(List<String> filterQuery, SolrRequest request) {
        List<String> remain = new ArrayList<>();
        for (String fq : filterQuery) {
            if (!fq.contains("#") && !fq.contains(CATEGORY)) {
                remain.add(fq);
                continue;
            }
            if (fq.contains(CATEGORY)) {
                String[] categoryArray = fq.split(":");
                String code = CategoryData.getInstance().getCategoryByName(request.getCore(), categoryArray[1]);
                if (StringUtils.isNotBlank(code)) {
                    remain.add("ColumnId" + ":" + code);
                }
                System.out.println("ColumnId" + ":" + code);
                continue;
            }
            if (fq.contains(County)) {
                String[] array = fq.split("#");
                if (array.length == 3) {
                    fq = County + ":" + array[2] + " AND " + City + ":" + array[1] + " AND " + array[0].replace(County, Province);
                    remain.add(fq);
                }
                continue;
            }
            if (fq.contains(City)) {
                String[] array = fq.split("#");
                if (array.length == 2) {
                    fq = City + ":" + array[1] + " AND " + array[0].replace(City, Province);
                    remain.add(fq);
                }
                continue;
            }
        }
        return remain;
    }
}
