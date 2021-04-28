package com.wanfangdata.grpc.server.db.dbservice;

import com.google.common.collect.BiMap;
import com.wanfangdata.grpc.server.db.dbentity.Column;
import com.wanfangdata.grpc.server.db.dbentity.Library;

import java.util.List;

/**
 * @Packagename com.wanfangdata.grpc.server.db.dbservice.impl
 * @Classname DataService
 * @Description
 * @Authors Mr.Wu
 * @Date 2020/09/02 16:04
 * @Version 1.0
 */
public interface DataService {


    /**
     * 获取数据库Id
     */
    Integer getLibrary(String alias);

    /**
     * 获取栏目导航(即一级栏目)
     */
    List<Column> columnsByAlias(String alias);

    /**
     * 获取栏目树映射表
     */
    BiMap<String, String> columnTreeByLibraryId(Integer libraryId);

    /**
     * 获取栏目树
     */
    Column columnTree(Integer libraryId);

    /**
     * 获取子栏目
     */
    List<Column> columnsByPid(Integer pid);

    /**
     * 获取数据库别名和数据库id映射表
     */
    BiMap<String, Library> library();

    /**
     * 获取数据库别名和数据库id映射表
     */
    BiMap<Integer, Library> Idlibrary();
}
