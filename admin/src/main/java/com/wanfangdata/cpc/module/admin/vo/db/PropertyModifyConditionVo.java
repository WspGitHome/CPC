package com.wanfangdata.cpc.module.admin.vo.db;

import com.wanfangdata.cpc.module.admin.vo.base.BaseConditionVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ProjectName: LocalChronicleGrpcSearch
 * @Package: com.wanfangdata.cpc.module.admin.vo.db
 * @ClassName: PropertyConditionVo
 * @Description: 资源修改记录查询条件
 * @Author: rongrong
 * @CreateDate: 2020/8/20
 * @Version: 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PropertyModifyConditionVo extends BaseConditionVo {
    private String proType;
    private String startDate;
    private String endDate;
}
