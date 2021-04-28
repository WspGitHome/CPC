package com.wanfangdata.cpc.module.admin.service.db.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanfangdata.cpc.module.admin.mapper.db.*;
import com.wanfangdata.cpc.module.admin.model.db.DbColumn;
import com.wanfangdata.cpc.module.admin.model.db.DbLibrary;
import com.wanfangdata.cpc.module.admin.model.db.DbLibtype;
import com.wanfangdata.cpc.module.admin.service.db.DbColumnService;
import com.wanfangdata.cpc.module.admin.vo.db.ColumnConditionVo;
import com.wanfangdata.cpc.module.admin.vo.db.ColumnTreeVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Linzhaoguan
 * @version V1.0
 * @date 2019年9月11日
 */
@Service
@AllArgsConstructor
public class DbColumnServiceImpl extends ServiceImpl<DbColumnMapper, DbColumn> implements DbColumnService {

    private final DbColumnMapper dbColumnMapper;
    private final DbLibraryMapper dbLibraryMapper;
    private final DbLibtypeMapper dbLibtypeMapper;
    private final DbContentMapper dbContentMapper;
    private final DbKeywordMapper dbKeywordMapper;
    @Override
    public List<DbColumn> findByCondition(IPage<DbColumn> page, ColumnConditionVo vo) {

        return dbColumnMapper.findByCondition(page,vo);
    }

    @Override
    public List<DbColumn> selecColumneAll(Integer library){
        return dbColumnMapper.selecColumneAll(library);
    }

    @Override
    public List<DbColumn> selecColumnes(DbColumn dbColum) {
        return dbColumnMapper.selecColumnes(dbColum);
    }

    @Override
    public int deleteBatch(Integer[] ids) {
        return dbColumnMapper.deleteBatch(ids);
    }

    @Override
    public DbColumn selectById(Integer id) {
        return dbColumnMapper.getById(id);
    }

    @Override
    public List<DbColumn> selectByPid(Integer pid) {
        return dbColumnMapper.selectList(Wrappers.<DbColumn>lambdaQuery().eq(DbColumn::getPid, pid));
    }

    @Override
    public List<DbColumn> selectParents(Integer id) {
        List<DbColumn> parents=new ArrayList<>();
        findParents(parents,id);
        return parents;
    }

    private void findParents(List<DbColumn> parents,Integer pid){
        DbColumn dbColumn=dbColumnMapper.getById(pid);
        parents.add(dbColumn);
        if(dbColumn!=null&&dbColumn.getPid()!=0){
            findParents(parents,dbColumn.getPid());
        }
    }

    @Override
    public ColumnTreeVo selectTree(Integer id) {
        DbColumn dbColumn = selectById(id);

        List<DbLibtype> libTypes=dbLibtypeMapper.selecLibtypes();
        List<DbLibrary> libraries=dbLibraryMapper.findAll(dbColumn.getLibType());
        List<DbColumn> parents=new ArrayList<>();
        if(dbColumn.getPid()!=0){
            parents=selectParents(dbColumn.getPid());
        }
        parents.add(dbColumn);
        Set<Integer> selected=new HashSet<>();
        parents.stream().forEach(dbColumn1 -> {
            selected.add(dbColumn1.getId());
        });

        List<DbColumn> list = selecColumneAll(dbColumn.getLibraryId());
        List<ColumnConditionVo> result = new ArrayList<>();
        list.stream().forEach(column -> {
            ColumnConditionVo vo=new ColumnConditionVo();
            vo.setId(column.getId());
            vo.setPid(column.getPid());
            vo.setName(column.getName());
            if(selected.contains(column.getId())){
                vo.setSelected(true);
            }
            result.add(vo);
        });

        ColumnTreeVo vo=new ColumnTreeVo();
        vo.setDbColumn(dbColumn);
        vo.setLibTypes(libTypes);
        vo.setLibraries(libraries);
        vo.setColumnTree(result);
        return vo;

    }
    @Override
    public DbColumn  columnTree(Integer libraryId) {
        List<DbColumn> list = selecColumneAll(libraryId);
        //虚拟的根栏目
        DbColumn column=new DbColumn();
        column.setId(0);
        column.setPid(0);
        column.setLibraryId(libraryId);
        return findChildrens(list,column);

    }

    private DbColumn findChildrens(List<DbColumn> dbColumns,DbColumn dbColumn){
        dbColumns.stream().forEach(it->{
            if(dbColumn.getId().equals(it.getPid())) {
                if (dbColumn.getChildren() == null) {
                    dbColumn.setChildren(new ArrayList<>());
                }
                //设置父栏目
                it.setParent(dbColumn);
                //设置子栏目
                dbColumn.getChildren().add(findChildrens(dbColumns,it));
            }
        });
        return dbColumn;
    }
    @Override
    public int insertColumn(DbColumn column) {
        return dbColumnMapper.insertColumn(column);
    }

    @Override
    public DbColumn moveUp(Integer sort) {
        return dbColumnMapper.moveUp(sort);
    }

    @Override
    public DbColumn moveDown(Integer sort) {
        return dbColumnMapper.moveDown(sort);
    }

    @Override
    public int deleteColumn(Integer id)  {
//        dbContentMapper.deleteByColumnId(id);
//        dbKeywordMapper.deleteByColumnId(id);
        return dbColumnMapper.deleteById(id);
    }
}
