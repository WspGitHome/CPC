package com.wanfangdata.cpc.module.admin.model.db;

import com.wanfangdata.cpc.module.admin.vo.base.BaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Packagename com.wanfangdata.cpc.module.admin.model.db
 * @Classname DbTemplate
 * @Description 数据库-模板 关联实体类
 * @Authors Mr.Wu
 * @Date 2020/08/07 15:11
 * @Version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DbTemplate extends BaseVo {
    private Integer dbId;
    private Integer templateId;
    private String bigAlbumLogo;
    private String smallAlbumLogo;
    private String homepagePicture;
    private String secondLevelPicture;
    private String thematicClassification;
    private String classificationPicture;
    private String areaClassification;
    private String areaClassificationPicture;
    private String buttonColor;
    private String clickColor;
    private String resourceTypeBookRead;
    private String resourceTypeItemRead;
    private String resourceTypeItemDownload;
    private String templateName;
    private String dbName;
    private String dbType;
    private Integer dbTypeId;

}
