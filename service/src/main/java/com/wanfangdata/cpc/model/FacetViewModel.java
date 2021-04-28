package com.wanfangdata.cpc.model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author FLY
 * @create
 **/
@ApiModel(value="检索条件",description="检索条件",parent = BaseSearchModel.class)
@AllArgsConstructor
@Data
public class FacetViewModel  extends BaseSearchModel{


    public FacetViewModel(){}

    /**
     * 一维聚类字段
     */
    @ApiModelProperty(value = "聚类字段",required = true)
    private  List<String> facets;


}
