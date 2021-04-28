package com.wanfangdata.cpc.module.admin.service.db;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wanfangdata.cpc.module.admin.model.db.DbColumn;
import com.wanfangdata.cpc.module.admin.vo.db.ColumnConditionVo;
import com.wanfangdata.cpc.module.admin.vo.db.ColumnTreeVo;

import java.util.List;

/**
 * @author Linzhaoguan
 * @version V1.0
 * @date 2019年9月11日
 */
public interface DbColumnService extends IService<DbColumn> {

    List<DbColumn> findByCondition(IPage<DbColumn> page, ColumnConditionVo vo);

    List<DbColumn> selecColumneAll(Integer library);

    List<DbColumn> selecColumnes(DbColumn dbColumn);

    int deleteBatch(Integer[] ids);

    DbColumn selectById(Integer id);

    List<DbColumn> selectByPid(Integer pid);

    List<DbColumn> selectParents(Integer id) ;

    ColumnTreeVo selectTree(Integer id) ;

    DbColumn  columnTree(Integer libraryId);
    /***
     * @description 新增
     * @param column
     * @return int
     * @authors rongrong
     * @date 2020/8/7
     * @modified by
     * @version 1.0
     */
    int insertColumn(DbColumn column);
    /***
     * @description  上移
     * @param sort
     * @return com.wanfangdata.cpc.module.admin.model.db.DbColumn
     * @authors rongrong
     * @date 2020/8/7
     * @modified by
     * @version 1.0
     */
    DbColumn moveUp(Integer sort);
    /***
     * @description 下移
     * @param sort
     * @return com.wanfangdata.cpc.module.admin.model.db.DbColumn
     * @authors rongrong
     * @date 2020/8/7
     * @modified by
     * @version 1.0
     */
    DbColumn moveDown(Integer sort);
    int deleteColumn(Integer id);
}
