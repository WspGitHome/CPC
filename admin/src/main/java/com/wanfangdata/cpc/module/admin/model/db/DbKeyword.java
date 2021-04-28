package com.wanfangdata.cpc.module.admin.model.db;

import com.wanfangdata.cpc.module.admin.vo.base.BaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @ProjectName: LocalChronicleGrpcSearch
 * @Package: com.wanfangdata.cpc.module.admin.model.db
 * @ClassName: DbKeyword
 * @Description: 关键词实体类
 * @Author: rongrong
 * @CreateDate: 2020/8/3
 * @Version: 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DbKeyword extends BaseVo {
    private static final long serialVersionUID = 7238198006412851178L;
    private Integer orderNum;
    private Integer dbType;
    private Integer libraryId;
    private String columnId;
    private String keywords;
    private String keywordName;
}
