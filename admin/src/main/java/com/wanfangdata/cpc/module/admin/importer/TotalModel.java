package com.wanfangdata.cpc.module.admin.importer;/**
 * @author FLY
 * @date 2019年7月19日
 */

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 *@program: LocalChronicleGrpcSearch88882
 *@description: 数据数量
 *@author: FLY
 *@create: 2020-08-27 10:25
 */
@Data
public class TotalModel {

    private Integer total=0;
    private List<Integer> list=new ArrayList<>();

    public void add(Integer num){
        total+=num;
        list.add(num);
    }
}