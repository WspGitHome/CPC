package com.wanfangdata.cpc.model;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.solr.client.solrj.beans.Field;

import java.util.List;
import java.util.Map;

/**
 * @author FLY
 * @create
 **/
@Data
@AllArgsConstructor
public class FacetResultViewModel {

    public FacetResultViewModel(){}
    /**
     * 返回聚类列表
     */
    @ApiModelProperty(value = "聚类列表")
    @Field("facets")
    Map<String,List<FacetModel>> facets;

}
