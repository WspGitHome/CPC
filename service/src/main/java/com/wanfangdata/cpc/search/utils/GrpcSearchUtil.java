package com.wanfangdata.cpc.search.utils;/**
 * @author FLY
 * @date 2019年7月19日
 */

import com.wanfangdata.cpc.entity.Library;
import com.wanfangdata.cpc.model.FacetModel;
import com.wanfangdata.cpc.model.FacetResultViewModel;
import com.wanfangdata.cpc.model.SearchResultViewModel;
import com.wanfangdata.cpc.utils.Constant;
import com.wanfangdata.search.*;

import java.util.*;

/**
 *@program: LocalChronicleGrpcSearch88882
 *@description: 检索结果转换
 *@author: FLY
 *@create: 2020-08-13 09:30
 */
public class GrpcSearchUtil {

    private final static int TITLELENGTH=50;

    private final static String QITA="其他";

    public static Map<String, String> resultToMap(Map<String, Value> resultMap,Integer libraryId) {
        return resultToMap(resultMap,null,0);
    }

    public static Map<String, String> resultToMap(Map<String, Value> resultMap, Map<String, Highlighting> highlightingMap,Integer libraryId) {
        Map<String, String> result = new HashMap<>();
        if(resultMap!=null) {
            if(libraryId!=0){
                result.put(Constant.LibraryId,String.valueOf(libraryId));
                //分类代码处理
                if(result.get(Constant.ColumnId)!=null){
                    Value columnIds=resultMap.get(Constant.ColumnId);
                    StringBuilder values=new StringBuilder();
                    if(columnIds.hasListValue()){
                        List<Value> valueList=columnIds.getListValue().getValuesList();
                        for(Value value:valueList){
                            String columnName=DataServiceUtil.getName(value.getStringValue(),libraryId);
                            values.append(columnName);
                        }
                        result.put(Constant.ColumnName,String.join(",",values));
                    }
                }
            }

            for (Map.Entry<String, Value> entry : resultMap.entrySet()) {
                Value value = entry.getValue();
                if (value.hasListValue()) {
                    List<Value> valueList=value.getListValue().getValuesList();
                    StringBuilder values=new StringBuilder();
                    for(int i=0;i<valueList.size();i++){
                        values.append(valueList.get(i).getStringValue());
                        if(i<valueList.size()-1){
                            values.append(",");
                        }
                    }
                    result.put(entry.getKey(), String.join(",", values.toString()));
                    continue;
                }
                String key=entry.getKey();
                if(value.getNumberValue()!=0){
                    Double valueDouble=value.getNumberValue();
                    result.put(key,String.valueOf(valueDouble.intValue()));
                    continue;
                }
                String valueString=value.getStringValue();
                Value newTitle=resultMap.get(Constant.NewTitle);
                if(newTitle!=null){
                    result.put(Constant.Title,newTitle.getStringValue());
                }
                if(Constant.isDateType(key)){
                    if(valueString.length()>10){
                        valueString=valueString.substring(0,10);
                    }
                }
                result.put(key,valueString);
            }
        }

        //高亮处理
        if(highlightingMap!=null){
            Highlighting highlighting=highlightingMap.get(result.get(Constant.ID));
            if(highlighting!=null){
                Map<String, ListValue> hlMap=highlighting.getHighlightingFieldsMap();
                for (Map.Entry<String, ListValue> entry:hlMap.entrySet()) {
                    String key=entry.getKey();
                    List<Value> valueList=entry.getValue().getValuesList();
                    StringBuilder values=new StringBuilder();
                    for(int i=0;i<valueList.size();i++){
                        values.append(valueList.get(i).getStringValue());
                        if(i<valueList.size()-1){
                            values.append(",");
                        }
                    }
                    String value=String.join(",", values.toString());
                    result.put(key+Constant.HL,value);
                }
            }
        }
        return result;
    }

    public static SearchResultViewModel searchResultToModel(SearchResponse response,String alias) {
        SearchResultViewModel model=new SearchResultViewModel();
        model.setTotalCount(response.getDocumentsCount());
        Library library=DataServiceUtil.getLibrary(alias);
        Map<String, Highlighting> highlightingMap=response.getHighlightResultsMap();
        if(response.getDocumentsList()!=null){
            List<Map<String,String>> list=new ArrayList<>();
            for(Document document:response.getDocumentsList()){
                Map<String, String>  map=resultToMap(document.getFieldsMap(),highlightingMap,library.getId());
                list.add(map);
            }
            model.setResult(list);
        }
        return model;
    }

    public static FacetResultViewModel facetResultToModel(FacetResponse response,String alias) {
        FacetResultViewModel model=new FacetResultViewModel();
        Map<String,List<FacetModel>> facets=new HashMap<>();
        Map<String, FacetResult> facetMap=response.getFacetResultsMap();
        if(facetMap!=null){
            for(Map.Entry<String, FacetResult> entry:facetMap.entrySet()){
                FacetResult facetResult=entry.getValue();
                String key=entry.getKey();
                if(Constant.ColumnId.equals(key)){
                    List<FacetModel> list=toFacetColumnModel(facetResult,alias);
                    facets.put(entry.getKey(),list);
                }else{
                    List<FacetModel> list=toFacetModel(facetResult);
                    facets.put(entry.getKey(),list);
                }

            }
        }
        model.setFacets(facets);
        return model;
    }

    private static List<FacetModel> toFacetModel(FacetResult facetResult){
        List<FacetModel> list=new ArrayList<>();
        if(facetResult!=null){
            List<Bucket> bucketList=facetResult.getBucketsList();
            FacetModel qita=null;
            for(Bucket bucket:bucketList){
                FacetModel facetModel=new FacetModel();
                facetModel.setCount((int)bucket.getCount());
                facetModel.setField(bucket.getText());
                facetModel.setValue(bucket.getVal());
                if(bucket.getSubFacetResultsMap()!=null){
                    for(Map.Entry<String, FacetResult> entry:bucket.getSubFacetResultsMap().entrySet()){
                        FacetResult facetResult1=entry.getValue();
                        List<FacetModel> list1=toFacetModel(facetResult1);
                        facetModel.setSubdata(list1);
                    }
                }
                if(QITA.equals(facetModel.getField())){
                    qita=facetModel;
                    continue;
                }
                list.add(facetModel);
            }
            if(qita!=null){
                list.add(qita);
            }
        }
        return list;
    }

    private static List<FacetModel> toFacetColumnModel(FacetResult facetResult,String alias){
        Map<String,FacetModel> map=new HashMap<>();
        Library library=DataServiceUtil.getLibrary(alias);
        Integer libraryId=library.getId();
        String libraryName=library.getName();
        if(facetResult!=null){
            //解析聚类
            List<Bucket> bucketList=facetResult.getBucketsList();
            for(Bucket bucket:bucketList){
                String columnId=bucket.getVal();
                if(columnId==null){
                    continue;
                }
                String columnName=DataServiceUtil.getName(columnId,libraryId);
                if(columnName==null||columnName.indexOf(Constant.separator_column)==-1){
                   continue;
                }
                String[] columnNames=columnName.split(Constant.separator_column);
                //分类代码处理
                if(columnId!=null&&columnId.contains(Constant.separator_column)){
                    String[] columnIdArray=columnId.split(Constant.separator_column);
                    if(!columnIdArray[0].equals(libraryId.toString())){
                        //过滤多值字段导致查出的非本库数据
                        continue;
                    }
                    //一级
                    if(columnIdArray.length>=2){
                        String columnId1=libraryId+Constant.separator_column+columnIdArray[1];
                        putToMap(map,libraryId,libraryId.toString(),columnId1,columnNames[1],bucket);
                        //包含二级
                        if(columnIdArray.length>=3){
                            //二级
                            String columnId2=libraryId+Constant.separator_column+columnIdArray[1]+Constant.separator_column+columnIdArray[2];
                            putToMap(map,libraryId,columnId1,columnId2,columnNames[2],bucket);
                            //三级
                            if(columnIdArray.length>=4){
                                String columnId3=libraryId+Constant.separator_column+columnIdArray[1]+Constant.separator_column+columnIdArray[2]+Constant.separator_column+columnIdArray[3];
                                putToMap(map,libraryId,columnId2,columnId3,columnNames[3],bucket);
                            }
                        }
                    }
                }

            }

        }
        //生成树
        return buildTreeParallel(map);
    }


    public static List<FacetModel> buildTreeParallel( Map<String,FacetModel> map) {
        List<FacetModel> result = new ArrayList<>();
        Collection<FacetModel> values = map.values();


        values.parallelStream().sorted(new Comparator<FacetModel>() {
            @Override
            public int compare(FacetModel o1, FacetModel o2) {
                if(QITA.equals(o1.getField())){//其他排在最后
                    return 1;
                }
                return o2.getCount()-o1.getCount();
            }
        }).forEachOrdered(value -> {
            if (value != null) {
                FacetModel treeDto = map.get(value.getParent());
                if (treeDto != null) {
                    List<FacetModel> children = treeDto.getSubdata();
                    if (children == null) {
                        children = new ArrayList<>();
                        treeDto.setSubdata(children);
                    }
                    children.add(value);
                } else {
                    result.add(value);
                }
            }
        });
        return result;
    }

    private static void putToMap(Map<String,FacetModel> map,Integer libraryId,String parent,String columnId,String columnName,Bucket bucket){
        if(map.containsKey(columnId)){
            FacetModel model=map.get(columnId);
            model.setCount(model.getCount()+(int)bucket.getCount());
        }else{
            FacetModel facetModel=new FacetModel();
            facetModel.setField(columnName);
            facetModel.setCount((int)bucket.getCount());
            facetModel.setValue(columnId);
            facetModel.setParent(parent);
            map.put(columnId,facetModel);
        }
    }

}