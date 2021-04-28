package com.wanfangdata.cpc.entity;/**
 * @author FLY
 * @date 2019年7月19日
 */

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *@program: LocalChronicleGrpcSearch88882
 *@description: 站点配置
 *@author: FLY
 *@create: 2020-08-14 13:25
 */
@Data
@AllArgsConstructor
public class Domain {

    public String domain;
    public String fulltext;
    public String login;
    public String logout;
    public String registered;
    public String imageserver;
}