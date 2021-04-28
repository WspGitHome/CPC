package com.wanfangdata.grpc.server.query.chain.filter.result;


import com.wanfangdata.api.chain.Filter;
import com.wanfangdata.api.chain.FilterException;
import com.wanfangdata.grpc.server.query.chain.entity.FacetEntity;
import com.wanfangdata.grpc.server.config.Config;
import com.wanfangdata.grpc.server.query.chain.SolrResponse;
import com.wanfangdata.grpc.server.query.chain.util.Constant;
import com.wanfangdata.grpc.server.query.chain.util.DataServiceUtil;
import com.wanfangdata.grpc.server.util.SpringContextUtil;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @author FLY
 * @date 2019-11-1
 */
public class FacetFilter implements Filter<QueryResponse, SolrResponse> {

    private static final String QITA = "其他";

    @Override
    public void doFilter(QueryResponse queryResponse, SolrResponse solrResponse) throws FilterException {
        Config config = (Config) SpringContextUtil.getBean("config");
        List<FacetField> facetList = queryResponse.getFacetFields();
        if (facetList == null || facetList.size() < 1) {
            return;
        }
        Map<String, List<FacetEntity>> facet = new HashMap<>();
        for (FacetField facetField : facetList) {
            //聚类字段
            String feild = facetField.getName();
            List<FacetEntity> mapToCountList = new ArrayList<FacetEntity>();
            if (config.getColumMap().get(feild) != null) {
                Map<String, FacetEntity> map = new HashMap<>();
                List<FacetField.Count> values = facetField.getValues();
                Integer libraryId = values.size() > 0 ? Integer.valueOf(values.get(0).getName().substring(0, values.get(0).getName().indexOf("/"))) : 1;
                for (FacetField.Count count : values) {
                    String columnId = count.getName();
                    if (columnId == null) {
                        continue;
                    }
                    if(Long.valueOf(count.getCount())==0){
                        continue;
                    }
                    String columnNameInit = DataServiceUtil.getName(columnId, libraryId);
                    if (columnNameInit == null || columnNameInit.indexOf(Constant.separator_wei) == -1) {
                        continue;
                    }
                    String columnName = "";
                    //分类代码处理
                    if (columnId != null && columnId.contains(Constant.separator_column)) {
                        String[] columnIdArray = columnId.split(Constant.separator_column);
                        if (!columnIdArray[0].equals(libraryId.toString())) {
                            //过滤多值字段导致查出的非本库数据
                            continue;
                        }
                        //一级
                        if (columnIdArray.length >= 2) {
                            String columnId1 = libraryId + Constant.separator_column + columnIdArray[1];
                            columnName = columnNameInit.substring(columnNameInit.indexOf(Constant.separator_wei) + 1);
                            if (columnIdArray.length == 3) {
                                columnName = columnName.substring(0, columnName.indexOf(Constant.separator_wei));
                            }
                            putToMap(map, libraryId.toString(), columnId1, columnName, count);
                            //包含二级
                            if (columnIdArray.length >= 3) {
                                //二级
                                columnName = columnNameInit.substring(columnNameInit.lastIndexOf(Constant.separator_wei) + 1);
                                String columnId2 = libraryId + Constant.separator_column + columnIdArray[1] + Constant.separator_column + columnIdArray[2];
                                putToMap(map, columnId1, columnId2, columnName, count);
                            }
                        }
                    }
                }
                mapToCountList = buildTreeParallel(map);
                mapToCountList = mapToCountList.stream().sorted(Comparator.comparingLong(FacetEntity::getCount).reversed()).collect(Collectors.toList());
                FacetEntity other = new FacetEntity();
                for (int i = 0; i < mapToCountList.size(); i++) {
                    FacetEntity currentBean = mapToCountList.get(i);
                    if (currentBean.getValue().equals(QITA)) {
                        other = currentBean;
                        mapToCountList.remove(i);
                        continue;
                    }
                    if (currentBean.getSubdata() != null && currentBean.getSubdata().size() > 0) {
                        List<FacetEntity> subdata = currentBean.getSubdata();
                        subdata = subdata.stream().sorted(Comparator.comparingLong(FacetEntity::getCount).reversed()).collect(Collectors.toList());
                        FacetEntity other2 = new FacetEntity();
                        for (int j = 0; j < subdata.size(); j++) {
                            FacetEntity currentBean2 = subdata.get(j);
                            if (currentBean2.getValue().equals(QITA)) {
                                other2 = currentBean2;
                                subdata.remove(j);
                                continue;
                            }
                        }
                        if (other2 != null && other2.getCount() != null) {
                            subdata.add(other2);
                        }
                        currentBean.setSubdata(subdata);
                    }
                }
                if (other != null && other.getCount() != null) {
                    mapToCountList.add(other);
                }

            } else {
                //聚类字段聚类结果（聚类值和数量）
                //把其他放在最后
                FacetEntity qita = null;
                for (int i = 0; i < facetField.getValues().size(); i++) {
                    Long count = facetField.getValues().get(i).getCount();
                    if (count.intValue()==0) {
                        continue;
                    }
                    //聚类字段聚类结果（聚类值和数量）
                    FacetEntity mapToCount = new FacetEntity();
                    String name = facetField.getValues().get(i).getName().trim();
                    mapToCount.setValue(name);
                    mapToCount.setCount(count);
                    if (QITA.equals(name) || QITA.equals(name)) {
                        qita = mapToCount;
                        continue;
                    }
                    if ("".equals(name)) {
                        continue;
                    }
                    mapToCountList.add(mapToCount);
                }
                if (qita != null) {
                    mapToCountList.add(qita);
                }
            }
            if (mapToCountList.get(0) != null && mapToCountList.get(0).getField().substring(0, mapToCountList.get(0).getField().indexOf("/")).equals("5")) {
                facet.put(feild, mapToCountList.get(0).getSubdata());
            } else {
                facet.put(feild, mapToCountList);
            }
        }
        solrResponse.setFacets(facet);
    }

    private List<FacetEntity> buildTreeParallel(Map<String, FacetEntity> map) {
        List<FacetEntity> result = new ArrayList<>();
        Set<? extends Map.Entry<String, ? extends FacetEntity>> entries = map.entrySet();
        entries.parallelStream().forEach(entry -> {
            FacetEntity value = entry.getValue();
            if (value != null && !value.getValue().equals("无")) {
                FacetEntity treeDto = map.get(value.getParent());
                if (treeDto != null) {
                    List<FacetEntity> children = treeDto.getSubdata();
                    children.add(value);
                    treeDto.setSubdata(children);
                } else {
                    result.add(value);
                }
            }
        });
        return result;
    }


    private static void putToMap(Map<String, FacetEntity> map, String parent, String columnId, String
            columnName, FacetField.Count bucket) {
        if (map.containsKey(columnId)) {
            FacetEntity model = map.get(columnId);
            model.setCount(model.getCount() + (int) bucket.getCount());
        } else {
            FacetEntity facetEntity = new FacetEntity();
            facetEntity.setField(columnId);
            facetEntity.setCount(bucket.getCount());
            facetEntity.setSubdata(new ArrayList<>());
            facetEntity.setValue(columnName);
            facetEntity.setParent(parent);
            map.put(columnId, facetEntity);
        }
    }
}
