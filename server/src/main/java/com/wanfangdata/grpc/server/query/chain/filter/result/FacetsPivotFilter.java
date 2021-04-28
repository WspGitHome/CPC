package com.wanfangdata.grpc.server.query.chain.filter.result;


import com.wanfangdata.api.chain.Filter;
import com.wanfangdata.api.chain.FilterException;
import com.wanfangdata.grpc.server.query.chain.SolrResponse;
import com.wanfangdata.grpc.server.query.chain.entity.FacetPivotEntity;
import org.apache.solr.client.solrj.response.PivotField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.util.NamedList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author FLY
 * @date 2019-11-1
 */
public class FacetsPivotFilter implements Filter<QueryResponse,SolrResponse> {

    private static final  String QITA = "其他";
    private static final  String QITA2 = "其它";

    //TODO 抽空看看能否改成递归形式
    @Override
    public void doFilter(QueryResponse queryResponse, SolrResponse solrResponse) throws FilterException {
        NamedList<List<PivotField>> facetPivotList = queryResponse.getFacetPivot();
        if (facetPivotList == null || facetPivotList.size() < 1) {
            return;
        }
        List<FacetPivotEntity> facetPivotEntityList = new ArrayList<>();
        List<PivotField> valueList = new ArrayList<PivotField>();
        for (Map.Entry<String, List<PivotField>> entry : facetPivotList) {
            valueList = entry.getValue();
        }
        FacetPivotEntity qita1 = null;
        for (PivotField pivotField : valueList) {
            FacetPivotEntity FacetPivotEntity = new FacetPivotEntity();
            List<FacetPivotEntity> list = new ArrayList<FacetPivotEntity>();
            String field = pivotField.getField();
            String fieldValue = (String) pivotField.getValue();
            int pivotFieldCount = pivotField.getCount();
            if (pivotFieldCount == 0) {
                continue;
            }
            List<PivotField> values = pivotField.getPivot();
            if (values != null && values.size() > 0) {
                FacetPivotEntity qita2 = null;
                for (PivotField value : values) {
                    String subField = value.getField();
                    String fieldValues = (String) value.getValue();
                    int valueCount = value.getCount();
                    if (valueCount == 0) {
                        continue;
                    }
                    FacetPivotEntity model = new FacetPivotEntity();
                    model.setValue(fieldValues);
                    model.setField(subField);
                    model.setCount(String.valueOf(valueCount));
                    if (QITA.equals(fieldValues) || QITA2.equals(fieldValues)) {
                        qita2 = model;
                        continue;
                    }
                    if("".equals(fieldValues)){
                        continue;
                    }
                    //////////////////county//////////////////
                    if(value.getPivot()!=null&& value.getPivot().size() > 0){
                        List<FacetPivotEntity> sublist = new ArrayList<FacetPivotEntity>();
                        List<PivotField> subvalues = value.getPivot();
                        FacetPivotEntity qita3 = null;
                        for (PivotField subvalue : subvalues) {
                            String subField1 = subvalue.getField();
                            String subfieldValues = (String) subvalue.getValue();
                            int subvalueCount = subvalue.getCount();
                            if (valueCount == 0) {
                                continue;
                            }
                            FacetPivotEntity submodel = new FacetPivotEntity();
                            submodel.setValue(subfieldValues);
                            submodel.setField(subField1);
                            submodel.setCount(String.valueOf(subvalueCount));
                            if (QITA.equals(subfieldValues) || QITA2.equals(subfieldValues)) {
                                qita3 = submodel;
                                continue;
                            }
                            if("".equals(subfieldValues)){
                                continue;
                            }

                            sublist.add(submodel);
                        }
                        if (qita2 != null) {
                            sublist.add(qita3);
                        }
                        model.setSubdata(sublist);

                    }
                    /////////////////county///////////////////
                    list.add(model);
                }
                if (qita2 != null) {
                    list.add(qita2);
                }
            }
            FacetPivotEntity.setField(field);
            FacetPivotEntity.setValue(fieldValue);
            FacetPivotEntity.setCount(String.valueOf(pivotFieldCount));
            FacetPivotEntity.setSubdata(list);
            if (QITA.equals(fieldValue) || QITA2.equals(fieldValue)) {
                qita1 = FacetPivotEntity;
                continue;
            }
            if(fieldValue==null||"".equals(fieldValue)){
                continue;
            }
            facetPivotEntityList.add(FacetPivotEntity);
        }
        if (qita1 != null) {
            facetPivotEntityList.add(qita1);
        }
        solrResponse.setFacetPivot(facetPivotEntityList);

    }
}
