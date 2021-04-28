package com.wanfangdata.cpc.module.admin.vo.db;

import com.wanfangdata.cpc.module.admin.vo.base.BaseConditionVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ProjectName: LocalChronicleGrpcSearch
 * @Package: com.wanfangdata.cpc.module.admin.vo.db
 * @ClassName: KeywordConditionVo
 * @Description: 描述类
 * @Author: rongrong
 * @CreateDate: 2020/8/3
 * @Version: 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class KeywordConditionVo extends BaseConditionVo {
    private Integer dbType;
    private Integer libraryId;
    private Integer columnId;
    private String keywordName;
    private Integer[] columnIds;
}
