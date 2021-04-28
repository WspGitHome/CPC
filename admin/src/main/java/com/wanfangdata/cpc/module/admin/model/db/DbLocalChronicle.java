package com.wanfangdata.cpc.module.admin.model.db;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @ProjectName: LocalChronicleGrpcSearch
 * @Package: com.wanfangdata.cpc.module.admin.model.db
 * @ClassName: DbLocalChronicle
 * @Description: 描述类
 * @Author: rongrong
 * @CreateDate: 2020/8/21
 * @Version: 1.0
 */
@EqualsAndHashCode()
@Data
public class DbLocalChronicle {
    private String id;
    private String newTitle;
    private String bookTitle;
    private String introduction;
    private String editorial;
    private String editor;
    private String publisher;
    private String date;
    private String year;
    private String iSBN;
    private String timeLimit;
    private String isNational;
    private String province;
    private String city;
    private String county;
    private String regionCode;
    private String regionCodeForSearch;
    private String categoryCode;
    private String categoryCodeForSearch;
    private String categoryLevel;
    private String regionLevel;
    private String albumCategory;
    private String keywords;
    private String volume;
    private String dynasty;
    private String reignTitle;
    private String reignYear;
    private String edition;
    private String pageCount;
    private String yearsForSearch;
    private String dBID;
    private Date onlineDate;
    private String boughtUserId;
    private String libraryId;
    private String columnId;
    private int batchId;
    private Date createTime;
    private Date updateTime;
}
