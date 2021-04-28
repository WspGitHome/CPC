package com.wanfangdata.cpc.model;/**
 * @author FLY
 * @date 2019年7月19日
 */

import com.wanfangdata.cpc.utils.Constant;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 *@program: LocalChronicleGrpcSearch88882
 *@description: 检索基础参数
 *@author: FLY
 *@create: 2020-08-13 18:06
 */
@Data
@AllArgsConstructor
public class BaseSearchModel {


    public BaseSearchModel(){}

    /**
     * 项目别名
     */
    @ApiModelProperty(value = "项目别名",required = true,allowableValues = Constant.ALIAS)
    private String alias;
    /**
     * 检索关键词
     */
    @ApiModelProperty(value = "检索词")
    private String query=Constant.DEFAULT_QUREY;
    /**
     * 过滤检索关键词
     */
    @ApiModelProperty(value = "过滤检索词</br>示例："+
            "</br>&nbsp;&nbsp;AlbumCategory:经济综合" +
            "</br>&nbsp;&nbsp;CategoryLevel:专业志" +
            "</br>&nbsp;&nbsp;Province:山东省"+
            "</br>&nbsp;&nbsp;City:青岛市")
    private List<String> fq;

    /**
     * 检索库地址
     */
    @ApiModelProperty(value = "要查询的资源库" ,required = true,allowableValues = Constant.PROPERTY)
    private String collection;
}