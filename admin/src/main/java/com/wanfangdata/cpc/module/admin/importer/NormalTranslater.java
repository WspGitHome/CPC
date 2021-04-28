package com.wanfangdata.cpc.module.admin.importer;

import com.wanfangdata.cpc.module.admin.model.db.DbColumn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 *@program: LocalChronicleGrpcSearch88882
 *@description: 将标引转为代码
 *@author: FLY
 *@create: 2020-08-07 10:22
 */
public class NormalTranslater implements Translater{

    Logger logger= LoggerFactory.getLogger(NormalTranslater.class);

    private final Map<String,String> translaterMap;
    private SimpleDateFormat simpleDateFormat;
    public NormalTranslater(DbColumn dbcolumnTree){
        simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.translaterMap= TranslaterUtil.translater(dbcolumnTree);
    }

    /***
     * 1.同一条数据，存在复分情况，在access中存储两行，分别标引栏目信息
     * 2.一条数据完整标引栏目树信息（即一级栏目二级栏目三级栏目）
     * 手工标引字段命名:
     *      一级栏目
     *      二级栏目
     *      三级栏目
     *
     */
    @Override
    public void translater(Map<String, Object> value,Integer libraryId) throws ImportException{
        Object  id1=value.get(Constant.ID);
        Object  id2=value.get(Constant.Id);
        String id=String.valueOf(id2!=null?id2:id1);
        String name="";
        String code="";
        if(StringUtil.isNotNull(value.get(Constant.CategoryC))){
            String CategoryC=StringUtil.nvl(value.get(Constant.CategoryC));
            String CategoryB=StringUtil.nvl(value.get(Constant.CategoryB));
            String CategoryA=StringUtil.nvl(value.get(Constant.CategoryA));
            name=CategoryA+Constant.separator_column+CategoryB+Constant.separator_column+CategoryC;

        }else if(StringUtil.isNotNull(value.get(Constant.CategoryB))){
            String CategoryB=StringUtil.nvl(value.get(Constant.CategoryB));
            String CategoryA=StringUtil.nvl(value.get(Constant.CategoryA));
            name=CategoryA+Constant.separator_column+CategoryB;
        }else if(StringUtil.isNotNull(value.get(Constant.CategoryA))){
            String CategoryA=StringUtil.nvl(value.get(Constant.CategoryA));
            name=CategoryA;
        }
        code=translaterMap.get(name);
        if(StringUtil.isNotNull(code)){
            value.put(Constant.ColumnId,code);
            value.put(Constant.LibraryId,libraryId);
        }else{
            if(name.contains(Constant.separator_column)){
                //找到未匹配的分类
                throw new ImportException(id,"找到未匹配的分类",null);
            }
            value.put(Constant.LibraryId,libraryId);
        }

        if(value.get(Constant.Id)==null&&value.get(Constant.ID)!=null){
            value.put(Constant.Id,value.get(Constant.ID));
        }
        if(value.get(Constant.NewTitle)==null&&value.get(Constant.Title)!=null){
            value.put(Constant.NewTitle,value.get(Constant.Title));
        }else if(value.get(Constant.NewTitle)==null&&value.get(Constant.BookTitle)!=null){
            value.put(Constant.NewTitle,value.get(Constant.BookTitle));
        }
        if(value.get(Constant.Keywords)==null){
            value.put(Constant.Keywords,value.get(Constant.KeywordsByMachine));
        }
        if(value.get(Constant.OnlineDate)!=null){
            try {
                Object dateObject=value.get(Constant.OnlineDate);
                if(dateObject instanceof Date){
                    Date date=(Date)dateObject;
                    String onlineDate=simpleDateFormat.format(date);
                    value.put(Constant.OnlineDate,onlineDate);
                }else if(dateObject instanceof String){
                    if(!dateObject.toString().equals("")){
                        value.put(Constant.OnlineDate,dateObject.toString());
                    }else{
                        value.put(Constant.OnlineDate,null);
                    }
                }
            }catch (Exception e){
                value.put(Constant.OnlineDate,null);
                logger.error("OnlineDate日期格式错误： id={} OnlineDate:{}",String.valueOf(value.get(Constant.Id)),String.valueOf(value.get(Constant.OnlineDate)));
            }

        }
        if(value.get(Constant.Date)!=null){
            try {
                Object dateObject=value.get(Constant.Date);
                if(dateObject instanceof Date){
                    Date date=(Date)dateObject;
                    String onlineDate=simpleDateFormat.format(date);
                    value.put(Constant.Date,onlineDate);
                }else if(dateObject instanceof String){
                    if(!dateObject.toString().equals("")){
                        value.put(Constant.Date,dateObject.toString());
                    }else{
                        value.put(Constant.Date,null);
                    }
                }
            }catch (Exception e){
                value.put(Constant.Date,null);
                logger.error("Date日期格式错误： id={} Date:{}",String.valueOf(value.get(Constant.Id)),String.valueOf(value.get(Constant.Date)));
            }

        }
        if(value.get(Constant.Year)!=null){
            try {
                Object object=value.get(Constant.Year);
                if(object instanceof Double){
                    Double d=(Double)object;
                    value.put(Constant.Year,d.intValue());
                }else{
                    int year=Integer.parseInt(String.valueOf(object));
                    value.put(Constant.Year,year);
                }
            }catch (Exception e){
                value.put(Constant.Year,0);
                logger.error("Year格式错误或为空： id={} Year:{}",String.valueOf(value.get(Constant.Id)),String.valueOf(value.get(Constant.Year)));
            }
        }
    }
}