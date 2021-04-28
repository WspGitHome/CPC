package com.wanfangdata.cpc.module.admin.model.db;

import com.baomidou.mybatisplus.annotation.TableField;
import com.wanfangdata.cpc.module.admin.vo.base.BaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class DbColumn extends BaseVo {
    private static final long serialVersionUID = 7238198006412851176L;

    private Integer pid;
    @TableField(exist = false)
    private Integer libType;
    private Integer libraryId;
    private Integer propertyId;
    private Integer sort;
    private String name;
    private String description;
    private String picture1;
    private String picture2;
    private String addUser;
    private String updateUser;

    @TableField(exist = false)
    private DbColumn parent;
    @TableField(exist = false)
    private List<DbColumn> children;

}
