package com.wanfangdata.cpc.module.admin.vo.db;

import com.wanfangdata.cpc.module.admin.model.db.DbColumn;
import com.wanfangdata.cpc.module.admin.model.db.DbLibrary;
import com.wanfangdata.cpc.module.admin.model.db.DbLibtype;
import lombok.Data;

import java.util.List;

/**
 * @author Linzhaoguan
 * @version V1.0
 * @date 2019年9月11日
 */
@Data
public class ColumnTreeVo  {

    /**
     * 当前栏目
     * */
    private DbColumn dbColumn;
    private List<DbLibtype> libTypes;
    private List<DbLibrary> libraries;
    /**
     * 已标引是否选中的栏目树
     * */
    private List<ColumnConditionVo> columnTree;

}

