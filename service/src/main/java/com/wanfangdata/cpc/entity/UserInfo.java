package com.wanfangdata.cpc.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author itguang
 * @create 2017-12-30 14:39
 **/
@Data
public class UserInfo  implements Serializable {

    @ApiModelProperty(value = "userId")
    private String userId;

    @ApiModelProperty(value = "用户名")
    private String userRealName;

    @ApiModelProperty(value = "昵称")
    private String userNickname;

    @ApiModelProperty(value = "机构名称")
    private String institution;

    @ApiModelProperty(value = "用户类型")
    private Integer userType;

    public UserInfo() {
    }

}
