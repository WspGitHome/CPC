package com.wanfangdata.grpc.server.query.chain.util;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.wanfangdata.grpc.server.db.dbentity.Library;
import com.wanfangdata.grpc.server.db.dbservice.DataService;
import com.wanfangdata.grpc.server.util.SpringContextUtil;

/**
 * @program: LocalChronicleGrpcSearch88882
 * @description: 将检索请求转为grpc请求
 * @author: FLY
 * @create: 2020-08-13 10:37
 */
public class DataServiceUtil {

    /**
     * 缓存每个数据库到代码的映射关系
     */
    private static BiMap<String, String> nameTocode = HashBiMap.create();
    /**
     * 缓存项目别名到LibraryId的映射
     */
    private static BiMap<String, Library> aliasToLibrary = HashBiMap.create();

    /**
     * 获取数据库Id
     */
    public static Library getLibrary(String alias) {
        Library library = aliasToLibrary.get(alias);
        if (library == null) {
            DataService dataService = (DataService) SpringContextUtil.getBean("dataServiceImpl");
            BiMap<String, Library> biMap = dataService.library();
            aliasToLibrary.putAll(biMap);
            library = aliasToLibrary.get(alias);
        }
        return library;
    }

    /**
     * 根据代码树获取名称树
     */
    public static String getName(String columnId, Integer libraryId) {
        String name = nameTocode.inverse().get(columnId);
        if (name == null) {
            initMap(libraryId);
            name = nameTocode.inverse().get(columnId);
        }
        return name;
    }

    /**
     * 根据名称树获取代码树
     */
    public static String getCode(String columnName, Integer libraryId) {
        String code = nameTocode.get(columnName);
        if (code == null) {
            initMap(libraryId);
            code = nameTocode.get(columnName);
        }
        return code;

    }

    private synchronized static void initMap(Integer libraryId) {
        DataService dataService = (DataService) SpringContextUtil.getBean("dataServiceImpl");
        BiMap<String, String> biMap = dataService.columnTreeByLibraryId(libraryId);
        nameTocode.putAll(biMap);
    }
}

