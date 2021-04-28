package com.wanfangdata.cpc.module.admin.service.db;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wanfangdata.cpc.module.admin.model.db.DbKeyword;
import com.wanfangdata.cpc.module.admin.vo.db.KeywordConditionVo;

/**
 * @ProjectName: LocalChronicleGrpcSearch
 * @Package: com.wanfangdata.cpc.module.admin.service.db.impl
 * @ClassName: DbKeywordService
 * @Description: 描述类
 * @Author: rongrong
 * @CreateDate: 2020/8/3
 * @Version: 1.0
 */
public interface DbKeywordService extends IService<DbKeyword> {
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
    IPage<DbKeyword> findByCondition(IPage<DbKeyword> page, KeywordConditionVo vo);

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
     * @description 插入
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

    Integer selectCountByColumnId(Integer columnId);
}
