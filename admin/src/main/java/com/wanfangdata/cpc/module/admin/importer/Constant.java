package com.wanfangdata.cpc.module.admin.importer;

import java.util.HashSet;
import java.util.Set;

/**
 *@program: LocalChronicleGrpcSearch88882
 *@description: 静态变量表
 *@author: FLY
 *@create: 2020-08-10 14:22
 */
public class Constant {

    public static final String separator_column="/";
    public static final String separator="%";
    public static final String separator_nomal="|%";
    //public static String  COLUMN_NAME="COLUMN_NAME";
    public static String  FIELD="Field";

    public static final String Id="Id";
    public static final String ID="ID";
    public static final String Title="Title";
    public static final String BookTitle="BookTitle";
    public static final String NewTitle="NewTitle";
    public static final String Keywords="Keywords";
    public static final String KeywordsByMachine="KeywordsByMachine";
    public static final String CategoryA="一级栏目";
    public static final String CategoryB="二级栏目";
    public static final String CategoryC="三级栏目";
    public static final String ColumnId="ColumnId";
    public static final String Content="Content";
    public static final String LibraryId="LibraryId";
    public static final String BatchId="BatchId";
    public static final String OnlineDate="OnlineDate";
    public static final String FulltextOnlineDate="FulltextOnlineDate";
    public static final String PublishDate="PublishDate";
    public static final String MeetingDate="MeetingDate";
    public static final String Date="Date";
    public static final String Year="Year";

    public static final String CreateTime="create_time";
    public static final String UpdateTime="update_time";


    private static Set<String> dateType=new HashSet<>();

    static {
        dateType.add(FulltextOnlineDate);
        dateType.add(OnlineDate);
        dateType.add(PublishDate);
        dateType.add(FulltextOnlineDate);
        dateType.add(MeetingDate);
        dateType.add(Date);
    }

    public static boolean isDateType(String date){
        return dateType.contains(date);
    }
}