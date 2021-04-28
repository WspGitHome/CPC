package com.wanfangdata.cpc.module.admin.model.db;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @ProjectName: LocalChronicleGrpcSearch
 * @Package: com.wanfangdata.cpc.module.admin.model.db
 * @ClassName: DbLocalChronicleItem
 * @Description: 描述类
 * @Author: rongrong
 * @CreateDate: 2020/8/21
 * @Version: 1.0
 */
@EqualsAndHashCode()
@Data
public class DbLocalChronicleItem {
    private String id;
    private String newTitle;
    private String title;
    private String parentID;
    private String libraryId;
    private String columnId;
    private String keywords;
    private String keywordsByMachine;
    private String realLevel;
    private String sheet;
    private String sheetInfo;
    private String chapter;
    private String chapterInfo;
    private String section;
    private String sectionInfo;
    private String item;
    private String itemInfo;
    private String subItem;
    private String subItemInfo;
    private String type;
    private String no;
    private String itemLevel;
    private String itemLevelCode;
    private String page;
    private String pageCount;
    private String content;
    private String location;
    private String beginPara;
    private String endPara;
    private String bookID;
    private String bookTitle;
    private String editorial;
    private String editor;
    private String publisher;
    private Date date;
    private int year;
    private String iSBN;
    private String timeLimit;
    private String timeLimitBegin;
    private String timeLimitEnd;
    private String isNational;
    private String province;
    private String city;
    private String county;
    private String regionCode;
    private String originalCategoryCode;
    private String categoryCode;
    private String regionCodeForSearch;
    private String categoryCodeForSearch;
    private String dBID;
    private String dynasty;
    private String reignTitle;
    private String reignYear;
    private String volume;
    private String volumeSort;
    private String filePath;
    private String yearsForSearch;
    private Date createTime;
    private Date updateTime;

}
