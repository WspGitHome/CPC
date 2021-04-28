package com.wanfangdata.cpc.module.admin.model.db;


import com.wanfangdata.cpc.module.admin.vo.base.BaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DbLibrary extends BaseVo {
    private static final long serialVersionUID = 7238198006412851176L;
    private String type;
    private String name;
    private String introduction;
    private String aliasPath;
}
