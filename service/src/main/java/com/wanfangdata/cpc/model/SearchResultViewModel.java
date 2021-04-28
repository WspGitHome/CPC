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
public class SearchResultViewModel {

    public SearchResultViewModel(){}
    /**
     * 查询结果
     */
    @ApiModelProperty(value = "结果列表")
    @Field("result")
    List<Map<String,String>> result;

    /**
     * 返回命中数
     */
    @ApiModelProperty(value = "命中条数")
    @Field("totalCount")
    Integer totalCount = 0;
    /**
     * 当前页数
     */
    @ApiModelProperty(value = "当前页数")
    @Field("curPage")
    int curPage = 0;
    /**
     * 总页数
     */
    @ApiModelProperty(value = "每页数量")
    @Field("pageSize")
    int pageSize = 0;
    /**
     * 总页数
     */
    @ApiModelProperty(value = "总页数")
    @Field("totalPage")
    int totalPage = 0;


}
