package com.wanfangdata.cpc.module.admin.model.db;


import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @ProjectName: LocalChronicleGrpcSearch
 * @Package: com.wanfangdata.cpc.module.admin.controller.db
 * @ClassName: PropertyList
 * @Description: 资源列表
 * @Author: rongrong
 * @CreateDate: 2020/8/12
 * @Version: 1.0
 */
@EqualsAndHashCode()
@Data
public class DbPropertyList  {
    private String proId;
    private String newTitle;
    private String title;
    private String columnId;
    private String columnName;
    private Date updateTime;

}
