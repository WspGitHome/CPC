package com.wanfangdata.cpc.module.admin.model.db;

import com.baomidou.mybatisplus.annotation.TableField;
import com.wanfangdata.cpc.module.admin.vo.base.BaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class DbProperty extends BaseVo {
    private static final long serialVersionUID = 7238198006412851176L;

    private String propertyName;
    private String propertyTableName;
    private String propertySolrAlias;
    private Boolean isCreate;
    private String addUser;
    private String updateUser;
    @TableField(exist = false)
    private DbPropertyList proList;
}
