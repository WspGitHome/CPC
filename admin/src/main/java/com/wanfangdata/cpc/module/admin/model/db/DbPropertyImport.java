package com.wanfangdata.cpc.module.admin.model.db;

import com.baomidou.mybatisplus.annotation.TableField;
import com.wanfangdata.cpc.module.admin.vo.base.BaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DbPropertyImport extends BaseVo {
    private static final long serialVersionUID = 7238198006412851176L;

    private String fileName;
    private String fileNameReal;
    private String filePath;
    private Integer status;
    @TableField(exist = false)
    private Integer libType;
    private Integer libraryId;
    @TableField(exist = false)
    private String libraryName;
    private Integer propertyId;
    @TableField(exist = false)
    private String propertyName;
    @TableField(exist = false)
    private String propertyTableName;

}
