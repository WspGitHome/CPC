package com.wanfangdata.grpc.server.db.dbservice.impl;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.wanfangdata.grpc.server.db.dbentity.Column;
import com.wanfangdata.grpc.server.db.dbentity.Library;
import com.wanfangdata.grpc.server.db.dbmapper.DataMapper;
import com.wanfangdata.grpc.server.db.dbservice.DataService;
import com.wanfangdata.grpc.server.query.chain.util.Constant;
import com.wanfangdata.grpc.server.util.TranslaterUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: LocalChronicleGrpcSearch88882
 * @description: 获取配置信息等
 * @author: FLY
 * @create: 2020-08-13 09:50
 */
@Service
@AllArgsConstructor
public class DataServiceImpl implements DataService {

    private DataMapper dataMapper;


    @Override
    public Integer getLibrary(String alias) {
        return dataMapper.getLibraryId(alias);
    }

    @Override
    public List<Column> columnsByAlias(String alias) {
        return dataMapper.columnsByAlias(alias);
    }

    @Override
    public List<Column> columnsByPid(Integer pid) {
        return dataMapper.columnsByPid(pid);
    }

    @Override
    public BiMap<String, String> columnTreeByLibraryId(Integer libraryId) {
        BiMap<String, String> map = TranslaterUtil.translater(columnTree(libraryId));
        return map;
    }


    @Override
    public BiMap<String, Library> library() {
        BiMap<String, Library> map = HashBiMap.create();
        List<Library> list = dataMapper.library();
        if (list != null && list.size() > 0) {
            for (Library library : list) {
                map.put(library.getAliasPath(), library);
            }
        }
        return map;
    }

    @Override
    public BiMap<Integer, Library> Idlibrary() {
        BiMap<Integer, Library> map = HashBiMap.create();
        List<Library> list = dataMapper.library();
        if (list != null && list.size() > 0) {
            for (Library library : list) {
                map.put(library.getId(), library);
            }
        }
        return map;
    }

    @Override
    public Column columnTree(Integer libraryId) {
        List<Column> columnList = dataMapper.columnTreeByLibraryId(libraryId);
        //虚拟的根栏目
        Column column = new Column();
        column.setId(0);
        column.setPid(libraryId);
        column.setColumnId(String.valueOf(libraryId));
        return findChildrens(columnList, column);

    }

    private Column findChildrens(List<Column> columns, Column column) {
        for (Column it : columns) {
            if (column.getId().equals(it.getPid())) {
                if (column.getChildren() == null) {
                    column.setChildren(new ArrayList<>());
                }
                //设置子栏目
                it.setColumnId(column.getColumnId() + Constant.separator_column + it.getId());
                column.getChildren().add(findChildrens(columns, it));
            }
        }
        return column;
    }

}