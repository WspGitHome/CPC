package com.wanfangdata.cpc.module.admin.mapper.db;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wanfangdata.cpc.module.admin.model.db.DbColumn;
import com.wanfangdata.cpc.module.admin.vo.db.ColumnConditionVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DbColumnMapper extends BaseMapper<DbColumn> {

    List<DbColumn> findByCondition(@Param("page") IPage<DbColumn> page, @Param("vo") ColumnConditionVo vo);

    List<DbColumn> selecColumneAll(@Param("libraryId") Integer library);

    List<DbColumn> selecColumnes(DbColumn dbColumn);

    List<Integer> getColumnIdByCondition(@Param("vo") ColumnConditionVo vo);

    int deleteBatch(Integer[] ids);

    DbColumn getById(Integer id);
    /*** 
     * @description 
     * @param column
     * @return int
     * @authors rongrong
     * @date 2020/8/7
     * @modified by
     * @version 1.0
     */
    int insertColumn(DbColumn column);
    /*** 
     * @description 
     * @param sort
     * @return com.wanfangdata.cpc.module.admin.model.db.DbColumn
     * @authors rongrong
     * @date 2020/8/7
     * @modified by
     * @version 1.0
     */
    DbColumn moveUp(Integer sort);
    /*** 
     * @description 
     * @param sort
     * @return com.wanfangdata.cpc.module.admin.model.db.DbColumn
     * @authors rongrong
     * @date 2020/8/7
     * @modified by
     * @version 1.0
     */
    DbColumn moveDown(Integer sort);


    int deleteByLibId(Integer libId);
}
