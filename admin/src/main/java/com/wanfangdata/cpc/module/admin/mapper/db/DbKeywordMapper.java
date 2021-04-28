package com.wanfangdata.cpc.module.admin.mapper.db;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wanfangdata.cpc.module.admin.model.db.DbKeyword;
import com.wanfangdata.cpc.module.admin.vo.db.KeywordConditionVo;
import org.apache.ibatis.annotations.Param;

/**
 * @ProjectName: LocalChronicleGrpcSearch
 * @Package: com.wanfangdata.cpc.module.admin.mapper.db
 * @ClassName: DbKeywordMapper
 * @Description: 描述类
 * @Author: rongrong
 * @CreateDate: 2020/8/3
 * @Version: 1.0
 */
public interface DbKeywordMapper extends BaseMapper<DbKeyword> {
    /***
     * @description 列表查询
     * @param page
     * @param vo
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.wanfangdata.cpc.module.admin.model.db.DbKeyword>
     * @authors rongrong
     * @date 2020/8/7
     * @modified by
     * @version 1.0
     */
    IPage<DbKeyword> findByCondition(@Param("page") IPage<DbKeyword> page, @Param("vo") KeywordConditionVo vo);
    /***
     * @description 删除
     * @param ids
     * @return int
     * @authors rongrong
     * @date 2020/8/7
     * @modified by
     * @version 1.0
     */
    int deleteBatch(Integer[] ids);
    /***
     * @description  插入
     * @param keyword
     * @return int
     * @authors rongrong
     * @date 2020/8/7
     * @modified by
     * @version 1.0
     */
    int insertKeyword(DbKeyword keyword);
    /***
     * @description 上移
     * @param orderNum
     * @return com.wanfangdata.cpc.module.admin.model.db.DbKeyword
     * @authors rongrong
     * @date 2020/8/7
     * @modified by
     * @version 1.0
     */
    DbKeyword moveUp(Integer orderNum);
    /***
     * @description 下移
     * @param orderNum
     * @return com.wanfangdata.cpc.module.admin.model.db.DbKeyword
     * @authors rongrong
     * @date 2020/8/7
     * @modified by
     * @version 1.0
     */
    DbKeyword moveDown(Integer orderNum);
    int deleteByLibId(Integer libId);
    int deleteByColumnId(Integer id);
}
