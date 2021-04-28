package com.wanfangdata.cpc.module.admin.vo.db;

import com.wanfangdata.cpc.module.admin.vo.base.BaseConditionVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ProjectName: LocalChronicleGrpcSearch
 * @Package: com.wanfangdata.cpc.module.admin.vo.db
 * @ClassName: PropertyConditionVo
 * @Description: 资源查询条件
 * @Author: rongrong
 * @CreateDate: 2020/8/12
 * @Version: 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PropertyConditionVo extends BaseConditionVo {
    private String propId;
    private String proType;
    private String title;
    private String libraryId;
    private String columnId;
}
