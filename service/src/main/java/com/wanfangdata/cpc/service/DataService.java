package com.wanfangdata.cpc.service;


import com.google.common.collect.BiMap;
import com.wanfangdata.cpc.entity.*;
import com.wanfangdata.cpc.model.PageResultModel;

import java.util.List;
import java.util.Map;

public interface DataService {
    /**
     * 获取模板信息
     * */
    Template getTemplate(String alias);
    /**
     * 获取数据库Id
     * */
    Integer getLibrary(String alias);
    /**
     * 获取栏目导航(即一级栏目)
     * */
    List<Column> columnsByAlias(String alias);
    /**
     * 获取栏目树映射表
     * */
    BiMap<String,String> columnTreeByLibraryId(Integer libraryId);
    /**
     * 获取栏目树
     * */
    Column columnTree(Integer libraryId);
    /**
     * 获取子栏目
     * */
    List<Column> columnsByPid(Integer pid);

    /**
     * 获取所有资源类型，及其对应的solr库
     * */
    List<Property> property();

    /**
     *获取关键词
     * */
    PageResultModel keywords(Integer columnId, Integer pageNo, Integer pageSize);
    /**
     *获取内容列表
     * */
    PageResultModel articles(Integer columnId, Integer pageNo, Integer pageSize);
    /**
     *获取内容
     * */
    Article article(Integer articleId);
    /**
     * 获取数据库别名和数据库id映射表
     * */
    BiMap<String,Library> library();

}
