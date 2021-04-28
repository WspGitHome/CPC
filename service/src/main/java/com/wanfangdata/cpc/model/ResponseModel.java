package com.wanfangdata.cpc.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Linzhaoguan
 * @version V1.0
 * @date 2019年9月11日
 */
@Data
@AllArgsConstructor
@ApiModel(value="接口返回结果",description="接口返回结果")
public class ResponseModel<T> {

    @ApiModelProperty(value = "返回状态码")
    private Integer status;
    @ApiModelProperty(value = "返回信息描述")
    private String msg;
    @ApiModelProperty(value = "返回数据")
    private T data;

    public ResponseModel(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

}
