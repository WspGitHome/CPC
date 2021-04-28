package com.wanfangdata.cpc.search.utils;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.wanfangdata.cpc.entity.Library;
import com.wanfangdata.cpc.service.DataService;
import com.wanfangdata.cpc.utils.SpringContextUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *@program: LocalChronicleGrpcSearch88882
 *@description: 将检索请求转为grpc请求
 *@author: FLY
 *@create: 2020-08-13 10:37
 */
public class DataServiceUtil {

    /**
     * 缓存每个数据库到代码的映射关系
     * */
    private static BiMap<String,String> nameTocode= HashBiMap.create();

    /**
     * initset 是否已经初始化
     * */
    private static Set<Integer> initSet=new HashSet<>();
    /**
     * 缓存项目别名到LibraryId的映射
     * */
    private static Map<String,Library> aliasToLibrary= new HashMap();

    /**
     * 获取数据库Id
     * */
    public static Library getLibrary(String alias){
        Library library=aliasToLibrary.get(alias);
        if(library==null){
            initAliasToLibrary();
            library=aliasToLibrary.get(alias);
        }
        return library;
    }

    /**
     * 根据代码树获取名称树
     * */
    public static String getName(String columnId,Integer libraryId){
        String name=nameTocode.inverse().get(columnId);
        if(name==null&&!initSet.contains(libraryId)){
            initMap(libraryId);
            name=nameTocode.inverse().get(columnId);
        }
        return name;
    }

    /**
     * 根据名称树获取代码树
     * */
    public static String getCode(String columnName,Integer libraryId){
        String code=nameTocode.get(columnName);
        if(code==null&&!initSet.contains(libraryId)){
            initMap(libraryId);
            code=nameTocode.get(columnName);
        }
        return code;
    }

    private synchronized static void initAliasToLibrary() {
        DataService dataService=(DataService) SpringContextUtil.getBean(DataService.class);
        BiMap<String,Library> biMap=dataService.library();
        aliasToLibrary.putAll(biMap);
    }

    private synchronized static void initMap(Integer libraryId) {
        initSet.add(libraryId);
        DataService dataService = (DataService) SpringContextUtil.getBean("dataServiceImpl");
        BiMap<String, String> biMap = dataService.columnTreeByLibraryId(libraryId);
        nameTocode.putAll(biMap);
    }
}