package com.wanfangdata.cpc.entity;

import lombok.Data;

import java.io.Serializable;

/**
 *@program: LocalChronicleGrpcSearch88882
 *@description: 配置集
 *@author: FLY
 *@create: 2020-08-14 13:20
 */
@Data
public class Template  implements Serializable {

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
}