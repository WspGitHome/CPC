package com.wanfangdata.cpc.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author FLY
 * @create
 **/
@Data
@AllArgsConstructor
@ApiModel(value="二级聚类条件",description="检索条件",parent = BaseSearchModel.class)
public class FacetPivotViewModel extends BaseSearchModel{

    public FacetPivotViewModel(){}

    /**
     * 多维聚类第一级
     */
    @ApiModelProperty(value = "多维聚类第一级",required = true)
    private String first;
    /**
     * 多维聚类第二级
     */
    @ApiModelProperty(value = "多维聚类第二级",required = true)
    private String secend;

}
