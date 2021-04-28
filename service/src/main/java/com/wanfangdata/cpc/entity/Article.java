package com.wanfangdata.cpc.entity;/**
 * @author FLY
 * @date 2019年7月19日
 */

import lombok.Data;

import java.io.Serializable;

/**
 *@program: LocalChronicleGrpcSearch88882
 *@description: 内容文章
 *@author: FLY
 *@create: 2020-08-14 17:43
 */
@Data
public class Article implements Serializable {

    private Integer id;
    private String title;
    private String author;
    private String source;
    private String url;
    private String introduction;
    private String contentDetails;
    private String picture;
    private String updateTime;

}