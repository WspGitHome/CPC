package com.wanfangdata.cpc.module.admin.importer;

import com.wanfangdata.cpc.module.admin.model.db.DbColumn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *@program: LocalChronicleGrpcSearch88882
 *@description: 将标引转为代码
 *@author: FLY
 *@create: 2020-08-07 10:22
 */
public class TranslaterUtil {


    /**
     * 获取栏目名称到栏目代码的映射关系
     *  key         value
     *  风景名胜      7/24
     *  文物馆藏      7/32
     *  石刻/其他     7/31/1
     *
     * */
    public static Map<String,String> translater(DbColumn dbcolumnTree){
        Map<String,String> translater=new HashMap<>();
        return trans(translater,dbcolumnTree);
    }

    private static Map<String,String> trans( Map<String,String> translater,DbColumn dbcolumnTree){
        List<DbColumn> children= dbcolumnTree.getChildren();
        if(children!=null){
            for(DbColumn dbColumn:children){
                translater.put(getName(dbColumn.getName(),dbColumn),getCode(dbColumn.getId().toString(),dbColumn));
                if(dbColumn.getChildren()!=null){
                    trans(translater,dbColumn);
                }
            }
        }
        return translater;
    }
    /**
     * 递归获取code树
     * */
    private static String getCode(String code,DbColumn dbColumn){
        if(dbColumn.getPid()!=0){
            code= getCode(code,dbColumn.getParent())+Constant.separator_column+dbColumn.getId();
        }else{
            code=dbColumn.getLibraryId()+Constant.separator_column+dbColumn.getId();
        }
        return code;
    }

    /**
     * 递归获取name树
     * */
    private static String getName(String name,DbColumn dbColumn){
        if(dbColumn.getPid()!=0){
            name= getName(name,dbColumn.getParent())+Constant.separator_column+dbColumn.getName();
        }else{
            name=dbColumn.getName();
        }
        return name;
    }
}