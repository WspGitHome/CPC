package com.wanfangdata.grpc.server.util;


import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.wanfangdata.grpc.server.db.dbentity.Column;
import com.wanfangdata.grpc.server.query.chain.util.Constant;

import java.util.List;

/**
 * @program: LocalChronicleGrpcSearch88882
 * @description: 将标引转为代码
 * @author: FLY
 * @create: 2020-08-07 10:22
 */
public class TranslaterUtil {


    /**
     * 获取栏目名称到栏目代码的映射关系
     * key         value
     * 风景名胜      7/24
     * 文物馆藏      7/32
     * 石刻/其他     7/31/1
     */
    public static BiMap<String, String> translater(Column dbcolumnTree) {
        try {
            return trans(dbcolumnTree);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HashBiMap.create();
    }

    private static BiMap<String, String> trans(Column dbcolumnTree) throws Exception {
        BiMap<String, String> translater = HashBiMap.create();
        List<Column> children = dbcolumnTree.getChildren();
        if (children != null) {
            for (Column dbColumn : children) {
                String code = dbColumn.getLibraryId() + Constant.separator_column + dbColumn.getId();
                String name = dbColumn.getLibraryName() + Constant.separator_wei + dbColumn.getName();
                translater.put(name, code);
                if (dbColumn.getChildren() != null) {
                    for (Column dbColumn1 : dbColumn.getChildren()) {
                        String code1 = code + Constant.separator_column + dbColumn1.getId();
                        String name1 = name + Constant.separator_wei + dbColumn1.getName();
                        translater.put(name1, code1);
                        if (dbColumn1.getChildren() != null) {
                            for (Column dbColumn2 : dbColumn1.getChildren()) {
                                String code2 = code + Constant.separator_column + dbColumn2.getId();
                                String name2 = name1 + Constant.separator_wei + dbColumn2.getName();
                                translater.put(name2, code2);
                            }
                        }
                    }
                }
            }
        }
        return translater;
    }

}