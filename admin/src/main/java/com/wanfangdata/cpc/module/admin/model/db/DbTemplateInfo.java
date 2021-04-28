package com.wanfangdata.cpc.module.admin.model.db;

import com.wanfangdata.cpc.module.admin.vo.base.BaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Packagename com.wanfangdata.cpc.module.admin.model.db
 * @Classname DbTemplateInfo
 * @Description
 * @Authors Mr.Wu
 * @Date 2020/08/07 09:07
 * @Version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DbTemplateInfo extends BaseVo {
    private String templateName;
    private String templateUrl;

    private String bigAlbumLogo;
    private String smallAlbumLogo;
    private String homepagePicture;
    private String secondLevelPicture;
    private String classificationPicture;
    private String areaClassificationPicture;

}
