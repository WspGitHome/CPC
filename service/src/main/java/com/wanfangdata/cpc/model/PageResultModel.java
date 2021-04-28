package com.wanfangdata.cpc.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.solr.client.solrj.beans.Field;

import java.util.List;
import java.util.Map;

/**
 *@program: LocalChronicleGrpcSearch88882
 *@description:
 *@author: FLY
 *@create: 2020-09-03 10:31
 */
@Data
@AllArgsConstructor
public class PageResultModel<T> {

    List<T> result;
    Integer totalCount = 0;
    int curPage = 0;
    int pageSize = 0;
    int totalPage = 0;
}