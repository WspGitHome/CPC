package com.wanfangdata.grpc.server.query.chain.filter.result;


import com.wanfangdata.api.chain.Filter;
import com.wanfangdata.api.chain.FilterException;
import com.wanfangdata.grpc.server.query.chain.SolrResponse;
import com.wanfangdata.grpc.server.query.chain.entity.FacetEntity;
import com.wanfangdata.grpc.server.query.chain.entity.FacetPivotEntity;
import com.wanfangdata.grpc.server.query.chain.util.CategoryData;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.PivotField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.util.NamedList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author FLY
 * @date 2019-11-1
 */
public class CategoryFacetFilter implements Filter<QueryResponse,SolrResponse> {

    private static final  String CATEGORY = "CategoryA,CategoryB";
    private static final  String QITA = "其他";

    @Override
    public void doFilter(QueryResponse queryResponse, SolrResponse solrResponse) throws FilterException {
        NamedList<List<PivotField>> facetPivotList = queryResponse.getFacetPivot();
        if (facetPivotList == null || facetPivotList.size() < 1) {
            return;
        }
        List<FacetPivotEntity> facetPivotEntityList = new ArrayList<>();
        List<PivotField> valueList = facetPivotList.get(CATEGORY);
        if(valueList==null){
            return;
        }
        facetPivotList.remove(CATEGORY);
        FacetPivotEntity qitaFacetPivotEntity=null;
        for (PivotField pivotField : valueList) {
            FacetPivotEntity facetPivotEntity = new FacetPivotEntity();
            List<FacetPivotEntity> list = new ArrayList<>();
            String fieldValue = (String) pivotField.getValue();
            String name= CategoryData.getInstance().getCategoryByCode(solrResponse.getCore(),fieldValue);
            int pivotFieldCount = pivotField.getCount();
            if (pivotFieldCount == 0) {
                continue;
            }
            List<PivotField> values = pivotField.getPivot();
            if (values != null && values.size() > 0) {
                for (PivotField value : values) {
                    String fieldValues = (String) value.getValue();
                    String names= CategoryData.getInstance().getCategoryByCode(solrResponse.getCore(),fieldValues);
                    if(names==null||names.equals("")){
                        continue;
                    }
                    String[] nameArray=names.split("#");
                    boolean isRelated=nameArray!=null&&nameArray.length==2&&nameArray[0].equals(name);
                    if(!isRelated){
                        continue;
                    }
                    int valueCount = value.getCount();
                    if (valueCount == 0) {
                        continue;
                    }
                    FacetPivotEntity model = new FacetPivotEntity();
                    model.setValue(nameArray[1]);
                    model.setCount(String.valueOf(valueCount));
                    list.add(model);
                }
            }
            facetPivotEntity.setValue(name);
            facetPivotEntity.setCount(String.valueOf(pivotFieldCount));
            facetPivotEntity.setSubdata(list);
            if(name!=null&&QITA.contains(name)){
                qitaFacetPivotEntity=  facetPivotEntity;
                continue;
            }
            if(name==null||"".equals(name)){
                continue;
            }
            facetPivotEntityList.add(facetPivotEntity);
        }
        if(qitaFacetPivotEntity!=null){
            facetPivotEntityList.add(qitaFacetPivotEntity);
        }
        solrResponse.setFacetPivot(facetPivotEntityList);
    }
}
