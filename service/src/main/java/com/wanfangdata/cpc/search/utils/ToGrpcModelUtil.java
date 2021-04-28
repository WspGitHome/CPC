package com.wanfangdata.cpc.search.utils;

import com.wanfangdata.cpc.model.BaseSearchModel;
import com.wanfangdata.cpc.model.FacetPivotViewModel;
import com.wanfangdata.cpc.model.FacetViewModel;
import com.wanfangdata.cpc.model.SearchViewModel;
import com.wanfangdata.cpc.utils.Constant;
import com.wanfangdata.search.*;

import java.util.ArrayList;
import java.util.List;

/**
 *@program: LocalChronicleGrpcSearch88882
 *@description: 将检索请求转为grpc请求
 *@author: FLY
 *@create: 2020-08-13 10:37
 */
public class ToGrpcModelUtil {

    private static final String separator=":";
    private static final Integer facet_limit=200;

    /**
     * TODO alias解析
     * */
    public static SearchRequest convertSearch(SearchViewModel model){
        SearchRequest.Builder builder=SearchRequest.newBuilder();
        if(model.getCollection()!=null){
            builder.addCollections(model.getCollection());
        }
        String qurey = model.getQuery();
        builder.setQuery(defaultQurey(qurey.trim()));

        List<Filter> fq=handleFq(model);
        builder.addAllFilters(fq);

        builder.setRows(model.getPageSize());
        if(model.getPageNo()<1){
            model.setPageNo(1);
        }
        builder.setStart((model.getPageNo()-1)*model.getPageSize());

        /**
         * 设置高亮字段(GRPC检索服务只支持检索字段高亮)
         * 1,检索词不指定字段 则需要从高亮字段指定
         * 2,检索词指定字段，高亮检索词字段
         * */
        if(!Constant.DEFAULT_QUREY.equals(model.getQuery())&&model.getQuery().contains(":")){
            HighlightQuery.Builder hlquery=HighlightQuery.newBuilder();
            hlquery.setSimplePre("<em class=\"hl\">");
            hlquery.setSimplePost("</em>");
            String[] querys=model.getQuery().split("AND|OR|NOT");
            for(String feild:querys){
                String hl=feild.split(":")[0];
                hlquery.addFieldNames(hl);
            }
            builder.setHighlightQuery(hlquery.build());
        }else if(!model.getQuery().contains(":")&&model.getHighLights()!=null){
            List<String> hls=model.getHighLights();
            StringBuilder stringBuilder=new StringBuilder(builder.getQuery());
            for(int i=0;i<hls.size();i++){
                stringBuilder.append(" OR "+hls.get(i)+":"+model.getQuery());
            }
            builder.setQuery(stringBuilder.toString());

            HighlightQuery.Builder hlquery=HighlightQuery.newBuilder();
            hlquery.setSimplePre("<em class=\"hl\">");
            hlquery.setSimplePost("</em>");
            for(String feild:hls){
                hlquery.addFieldNames(feild);
            }
            builder.setHighlightQuery(hlquery.build());
        }

        //设置排序
        if(model.getSort()!=null){
            String sort=model.getSort();
            SortRequest.Builder sortRequest=SortRequest.newBuilder();
            String[] sorts=sort.split(separator);
            if(sorts.length==2){
                Sort.Order order=Sort.Order.valueOf(sorts[1]);
                Sort.Builder sortBuilder=Sort.newBuilder().setBy(sorts[0]).setOrder(order);
                sortRequest.addSorts(sortBuilder);
                builder.setSort(sortRequest);
            }
        }
        return builder.build();
    }


    public static FacetRequest convertFacet(FacetViewModel model){
        FacetRequest.Builder builder=FacetRequest.newBuilder();
        if(model.getCollection()!=null){
            builder.addCollections(model.getCollection());
        }
        String qurey = model.getQuery();
        builder.setQuery(defaultQurey(qurey.trim()));

        List<Filter> fq=handleFq(model);
        builder.addAllFilters(fq);

        if(model.getFacets()!=null){
           for(String facet:model.getFacets()){
               FacetQuery.Builder facetQuery=FacetQuery.newBuilder();
               facetQuery.setField(facet);
               facetQuery.setLimit(facet_limit);
               builder.putFacets(facet,facetQuery.build());
           }
        }
        return builder.build();
    }

    public static FacetRequest convertFacetPivot(FacetPivotViewModel model){
        FacetRequest.Builder builder=FacetRequest.newBuilder();
        if(model.getCollection()!=null){
            builder.addCollections(model.getCollection());
        }
        String qurey = model.getQuery();
        builder.setQuery(defaultQurey(qurey.trim()));

        List<Filter> fq=handleFq(model);
        builder.addAllFilters(fq);

        //grpc多维聚类只支持2级
        if(model.getFirst()!=null&&model.getSecend()!=null){
            String first=model.getFirst();
            String secend=model.getSecend();
            FacetQuery.Builder facetQuery=FacetQuery.newBuilder();
            facetQuery.setLimit(facet_limit);
            facetQuery.setField(first);
            FacetQuery.Builder subFacetQuery=FacetQuery.newBuilder();
            subFacetQuery.setLimit(facet_limit);
            subFacetQuery.setField(secend);
            facetQuery.putSubFacets(secend,subFacetQuery.build());
            builder.putFacets(first,facetQuery.build());
        }

        return builder.build();
    }

    private static List<Filter> handleFq(BaseSearchModel model){
        List<String> fq=model.getFq();

        List<Filter> list=new ArrayList<>();
        if(fq!=null){
            for(String entry:fq){
                String[] filters=entry.split(separator);
                if(filters.length==2){
                    Filter.Builder filter=Filter.newBuilder().setField(filters[0]).setValue(filters[1]);
                    list.add(filter.build());
                }
            }
        }

        String alias=model.getAlias();
        if(alias!=null) {
            Integer libraryId = DataServiceUtil.getLibrary(alias).getId();
            Filter.Builder filter = Filter.newBuilder().setField(Constant.LibraryId).setValue(libraryId.toString());
            list.add(filter.build());
        }
        return list;
    }

    private static String defaultQurey(String qurey) {
        if (qurey != null && !"".equals(qurey)) {
            return qurey;
        } else {
            return Constant.DEFAULT_QUREY;
        }
    }
}