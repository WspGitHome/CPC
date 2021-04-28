package com.wanfangdata.cpc.model;
import com.wanfangdata.cpc.utils.Constant;
import io.swagger.annotations.ApiModel;
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
@ApiModel(value="检索条件",description="检索条件SearchViewModel")
@AllArgsConstructor
@Data
public class SearchViewModel  extends BaseSearchModel{

    public SearchViewModel(){}

    /**
     * 页码
     */
    @ApiModelProperty(value = "查询页码",position = 3)
    private int pageNo=1;

    /**
     * 每页数量
     */
    @ApiModelProperty(value = "每页数量,默认"+Constant.PAGE_SIZE,position = 4)
    private int pageSize= Constant.PAGE_SIZE;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序 Title:DESC;Title:ASC" ,position = 6)
    private String sort;

    /**
     * 设置高亮字段
     */
    @ApiModelProperty(value = "需要高亮字段</br>",position = 7)
    private List<String> highLights;

    /**
     * 设置返回字段(由后台设置)
     */
    @ApiModelProperty(value = "指定返回字段",position = 7,hidden = true)
    private List<String> returnFeild;
}
