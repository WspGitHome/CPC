package com.wanfangdata.cpc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *@program: LocalChronicleGrpcSearch88882
 *@description: 配置集
 *@author: FLY
 *@create: 2020-08-14 13:20
 */
@Data
@AllArgsConstructor
public class Config {

    private Template template;
    private Domain domain;

}