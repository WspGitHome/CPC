package com.wanfangdata.cpc.entity;/**
 * @author FLY
 * @date 2019年7月19日
 */

import lombok.Data;

import java.io.Serializable;

/**
 *@program: LocalChronicleGrpcSearch88882
 *@description: 关键字
 *@author: FLY
 *@create: 2020-08-14 17:20
 */
@Data
public class Keyword  implements Serializable {

    private Integer id;
    private Integer orderNum;
    private String keywords;
    private String keywordName;
    private String columnName;
}