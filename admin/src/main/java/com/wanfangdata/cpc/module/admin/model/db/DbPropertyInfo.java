package com.wanfangdata.cpc.module.admin.model.db;

import com.wanfangdata.cpc.module.admin.vo.base.BaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class DbPropertyInfo extends BaseVo {
    private static final long serialVersionUID = 7238198006412851176L;

    private String propertyId;
    private String propertyChinaColumn;
    private String propertyColumn;
    private String propertyLength;
    private String propertyType;
}
