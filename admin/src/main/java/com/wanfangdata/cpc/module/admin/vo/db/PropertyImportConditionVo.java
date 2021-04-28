package com.wanfangdata.cpc.module.admin.vo.db;

import com.wanfangdata.cpc.module.admin.vo.base.BaseConditionVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Linzhaoguan
 * @version V1.0
 * @date 2019年9月11日
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PropertyImportConditionVo extends BaseConditionVo {

    private String fileName;
    private String fileNameReal;
    private String filePath;
    private Integer status;
    private String libraryId;
    private String propertyId;

}

