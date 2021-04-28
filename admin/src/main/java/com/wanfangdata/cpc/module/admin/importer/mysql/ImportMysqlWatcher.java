package com.wanfangdata.cpc.module.admin.importer.mysql;

import com.wanfangdata.cpc.module.admin.importer.*;
import com.wanfangdata.cpc.module.admin.model.db.DbIdForCategory;
import com.wanfangdata.cpc.module.admin.model.db.DbPropertyImport;
import com.wanfangdata.cpc.module.admin.service.db.PropertyService;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *@program: LocalChronicleGrpcSearch88882
 *@description: 记录出错情况并通知到前台
 *@author: FLY
 *@create: 2020-08-06 08:58
 */
@Data
public class ImportMysqlWatcher implements Watcher {

    private String reg = "[_`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\\n|\\r|\\t";

    private Pattern pt = Pattern.compile(reg);

    private static Integer MaxTolerate=200;

    private List<String> errorHolder=new ArrayList<>(MaxTolerate);
    private List<String> columns=new ArrayList<>();

    private final PropertyService propertyService;
    private final Translater translater;
    private final DbPropertyImport dbPropertyImport;

    private TotalModel totalModel =new TotalModel();

    private final String updateDate;


    public ImportMysqlWatcher(PropertyService propertyService, Translater translater, DbPropertyImport dbPropertyImport){
        this.propertyService=propertyService;
        this.translater=translater;
        this.dbPropertyImport=dbPropertyImport;

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.updateDate=simpleDateFormat.format(dbPropertyImport.getUpdateTime());

        List<Map<String ,Object>> listColumns=propertyService.selectSchama(dbPropertyImport.getPropertyTableName());
        for(Map<String,Object> column:listColumns){
            columns.add(String.valueOf(column.get(Constant.FIELD)));
        }
        columns.remove(Constant.CreateTime);
    }
    @Override
    public void recodeError(String error) {
        errorHolder.add(error);
    }

    @Override
    public void notifier() {

    }

    @Override
    public void process(List<Map<String, Object>> values) throws ImportException {
        //查询是否已经存在该条数据
        List<String> ids=new ArrayList<>();
        values.stream().forEach(map->{
            Object  id1=map.get(Constant.ID);
            Object  id2=map.get(Constant.Id);
            String id=String.valueOf(id2!=null?id2:id1);
            ids.add(id);
        });
        Map<String,DbIdForCategory> idsForCategoryMap=new HashMap<>();
        List<DbIdForCategory> idsForCategorys=propertyService.selectIdForCategory(dbPropertyImport.getPropertyTableName(),ids);
        if(idsForCategorys!=null&&idsForCategorys.size()!=0){
            for(DbIdForCategory dbIdForCategory:idsForCategorys){
                idsForCategoryMap.put(dbIdForCategory.getID(),dbIdForCategory);
            }
        }
        List<List<Object>> result=new ArrayList<>();
        for(Map<String, Object> value:values){
            List<Object> valueList=new ArrayList<>();
            //将中文标注转为代码,并进行格式处理
            try {
                translater.translater(value,dbPropertyImport.getLibraryId());
                //放入批次ID
                value.put(Constant.BatchId,dbPropertyImport.getId());
            }catch (ImportException e){
                recodeError(String.format(" 未找到匹配分类 :%s , %s, %s",e.getId(),e.getMsg(),e.getMessage()));
                break;
            }

            //查询是否已经存在数据拼接code
            String id=String.valueOf(value.get(Constant.Id));
            if(idsForCategoryMap.containsKey(id)){
                //合并栏目代码
                String oldCode=StringUtil.nvl(idsForCategoryMap.get(id).getColumnId());
                String appendCode=StringUtil.nvl(value.get(Constant.ColumnId));
                String[] codes=oldCode.split(Constant.separator);
                Set<String> mySet = new HashSet<String>();
                Collections.addAll(mySet, codes);
                mySet.add(appendCode);
                mySet.remove("null");
                String newCode=String.join(Constant.separator,mySet);
                value.put(Constant.ColumnId,newCode);
                //合并数据库代码
                String oldLibrary=StringUtil.nvl(idsForCategoryMap.get(id).getLibraryId());
                String appendLibrary=StringUtil.nvl(value.get(Constant.LibraryId));
                String[] libraryIds=oldLibrary.split(Constant.separator);
                Set<String> mySet1 = new HashSet<String>();
                Collections.addAll(mySet1, libraryIds);
                mySet1.add(appendLibrary);
                mySet1.remove("null");
                String newLibrary=String.join(Constant.separator,mySet1);
                value.put(Constant.LibraryId,newLibrary);
            }
            for(String column:columns){
                if(Constant.UpdateTime.equals(column)){
                    //updateTime 当做上线批次
                    valueList.add(updateDate);
                    continue;
                }
                Object object=value.get(column);
                if(object!=null&&object instanceof String){
                    String str=object.toString();
                    if(Constant.Content.equals(column)){
                        if(str.length()>MaxTolerate){
                            str.substring(0,MaxTolerate);
                        }
                        Matcher m = pt.matcher(str);
                        if (m.find()) {
                          str=m.replaceAll("");
                        }
                    }else{
                        str=str.replace("'","");
                    }
                    valueList.add(str);
                    continue;
                } if(object!=null&&object instanceof Double){
                    Double o=(Double)object;
                    valueList.add(String.valueOf(o.intValue()));
                    continue;
                }else{
                    valueList.add(object);
                }
            }

            result.add(valueList);
        }
        if(result.size()>0){
            try {
                propertyService.saveBatch(dbPropertyImport.getPropertyTableName(),columns,result);
            }catch (Exception e){
                recodeError("可能出错数据:"+ids.toString());
            }
        }
    }

    @Override
    public void setDataModel(TotalModel data) {
        this.totalModel=data;
    }

    public static void main(String[] args){
        String reg = "[_`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\\n|\\r|\\t";

        Pattern pt = Pattern.compile(reg);
        Matcher m = pt.matcher("aaa'aaa");
        System.out.print(m.replaceAll(""));
    }
}