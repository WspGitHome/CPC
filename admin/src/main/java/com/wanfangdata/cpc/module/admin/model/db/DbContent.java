package com.wanfangdata.cpc.module.admin.model.db;

import com.wanfangdata.cpc.module.admin.vo.base.BaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class DbContent extends BaseVo {
    private static final long serialVersionUID = 7238198006412851176L;
    private Integer dbType;
    private Integer libraryId;
    private String columnId;
    private String title;
    private String author;
    private String source;
    private String url;
    private String introduction;
    private String contentDetails;
    private String picture;
}
