package com.wanfangdata.cpc.module.admin.model.db;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;

/**
 * @ProjectName: LocalChronicleGrpcSearch
 * @Package: com.wanfangdata.cpc.module.admin.model.db
 * @ClassName: LocalChronicle
 * @Description: 志书实体类
 * @Author: rongrong
 * @CreateDate: 2020/8/17
 * @Version: 1.0
 */
@EqualsAndHashCode()
@Data
public class LocalChronicle  {
    private String id;
    private String bookTitle;
    private String newTitle;
    private String introduction;
    private String editorial;
    private String editor;
    private String publisher;
    private String province;
    private String city;
    private String county;
    private String keywords;
    private String columnId;
    private String libraryId;
    private String selBigClass;//一级栏目
    private String selSmallClass;//二级栏目
    private String selThreeClass;//三级栏目
    private Date updateTime;
}
