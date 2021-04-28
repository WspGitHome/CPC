package com.wanfangdata.cpc.module.admin.vo.db;

import com.baomidou.mybatisplus.annotation.TableField;
import com.wanfangdata.cpc.module.admin.model.BizCategory;
import com.wanfangdata.cpc.module.admin.vo.base.BaseConditionVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author Linzhaoguan
 * @version V1.0
 * @date 2019年9月11日
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ColumnConditionVo extends BaseConditionVo {

    private Integer id;
    private Integer pid;
    private Integer libType;
    private Integer libraryId;
    private String name;
    private String description;
    private Integer sort;
    private String createUser;
    private Boolean open = true;
    private Boolean selected = false;


}

