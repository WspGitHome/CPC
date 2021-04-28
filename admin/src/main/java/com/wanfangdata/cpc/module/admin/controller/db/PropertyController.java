package com.wanfangdata.cpc.module.admin.controller.db;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wanfangdata.cpc.common.util.CoreConst;
import com.wanfangdata.cpc.common.util.Pagination;
import com.wanfangdata.cpc.common.util.ResultUtil;
import com.wanfangdata.cpc.config.ApplicationInitialize;
import com.wanfangdata.cpc.module.admin.model.db.*;
import com.wanfangdata.cpc.module.admin.service.db.*;
import com.wanfangdata.cpc.module.admin.vo.base.PageResultVo;
import com.wanfangdata.cpc.module.admin.vo.base.ResponseVo;
import com.wanfangdata.cpc.module.admin.vo.db.PropertyConditionVo;
import lombok.AllArgsConstructor;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 后台资源管理
 *
 * @author Linzhaoguan
 * @version V1.0
 * @date 2019年9月11日
 */
@Controller
@RequestMapping("property")
@AllArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;
    private final PropertyListService propertyListService;
    private final DbLibService dbLibService;
    private final ApplicationInitialize applicationInitialize;
    private final DbColumnService dbColumnService;
    private final DbPropertyModifyService propertyModifyService;
    private final SolrClient solrClient;
    @PostMapping("list")
    @ResponseBody
    public PageResultVo loadProperty(PropertyConditionVo conditionVo, Integer pageNumber, Integer pageSize) {
        IPage<DbPropertyList> page = new Pagination<>(pageNumber, pageSize);
        IPage<DbPropertyList> propertyList = propertyService.findByCondition(page, conditionVo);
        return ResultUtil.table(propertyList.getRecords(), propertyList.getTotal());
    }

    @GetMapping("/add")
    public String add(Model model) {
        List<DbLibtype> libraries=dbLibService.selecLibtypes();
        model.addAttribute("libraries", libraries);
        List<DbProperty> properties=propertyService.list();
        model.addAttribute("properties", properties);
        return CoreConst.ADMIN_PREFIX + "property/add";
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseVo add(DbProperty dbProperty) {
        return null;
    }

    @GetMapping("/edit")
    public String edit(Model model, String proId,String proType) {
        model.addAttribute("properties",applicationInitialize.getDbProperties());
        if (proType.equals("localChronicle")){
            LocalChronicle localChronicle = propertyListService.selectById(proId);
            String columnId=localChronicle.getColumnId();
            model.addAttribute("localChronicle", localChronicle);
            model.addAttribute("columns", getColumns(columnId));
            return CoreConst.ADMIN_PREFIX + "property/localChronicle";
        }else {
            LocalChronicleItem item = propertyListService.selectItemById(proId);
            String columnId=item.getColumnId();
            model.addAttribute("item", item);
            model.addAttribute("columns", getColumns(columnId));
            return CoreConst.ADMIN_PREFIX + "property/localChronicleItem";
        }
    }

    @PostMapping("/edit/localChronicle")
    @ResponseBody
    public ResponseVo editLocalChronicle(LocalChronicle localChronicle) {
        localChronicle.setUpdateTime(new Date());
        boolean i = propertyListService.updateLocalChronicle(localChronicle);
        savePropertyModify("localChronicle",localChronicle.getId());
//        updateSolr(localChronicle);
        if (i) {
            return ResultUtil.success("编辑志书成功");
        } else {
            return ResultUtil.error("编辑志书失败");
        }
    }
    @PostMapping("/edit/item")
    @ResponseBody
    public ResponseVo editItem( LocalChronicleItem item) {
        item.setUpdateTime(new Date());
        boolean i = propertyListService.updateLocalChronicleItem(item);
        savePropertyModify("localChronicleItem",item.getId());
//        updateItemSolr(item);
        if (i) {
            return ResultUtil.success("编辑志书条目成功");
        } else {
            return ResultUtil.error("编辑志书条目失败");
        }
    }
    @PostMapping("/delete")
    @ResponseBody
    public ResponseVo delete(String proId,String proType) {
        int i = propertyService.deletePro(proId,proType);
        if (i > 0) {
            return ResultUtil.success("删除资源成功");
        } else {
            return ResultUtil.error("删除资源失败");
        }
    }
/***
 * @description 拼接栏目编号
 * @param LibraryId
 * @param columnOne
 * @param columnTwo
 * @param columnThree
 * @return java.lang.String
 * @authors rongrong
 * @date 2020/8/19
 * @modified by
 * @version 1.0
 */
    public String getColumnId(String LibraryId,String columnOne,String columnTwo,String columnThree){
         String columnId="";
         if(columnOne==null ||columnOne.equals("一级栏目")){
             columnId=LibraryId+"/";
         }else if(columnTwo==null || columnTwo.equals("二级栏目")){
             columnId=LibraryId+"/"+columnOne;
         }else if(columnThree==null|| columnThree.equals("三级栏目")){
             columnId=LibraryId+"/"+columnOne+"/"+columnTwo;
         }else{
             columnId=LibraryId+"/"+columnOne+"/"+columnTwo+"/"+columnThree;
         }
        return columnId;
    }
/***
 * @description 将资源修改数据同步到修改记录表
 * @param propertyType
 * @param propertyId
 * @return boolean
 * @authors rongrong
 * @date 2020/8/19
 * @modified by
 * @version 1.0
 */
    public boolean savePropertyModify(String propertyType,String propertyId){
        Integer proIdCount = propertyModifyService.ifPropertyId(propertyId);
        DbPropertyModify modify=new DbPropertyModify();
        modify.setPropertyType(propertyType);
        modify.setPropertyId(propertyId);
        modify.setUpdateTime(new Date());
        if(proIdCount==0){
            return propertyModifyService.save(modify);
        }else{
            return   propertyModifyService.updateProperty(modify);
        }

    }

    private List getColumns(String columnId){
        List<String> columns=new ArrayList<>();
        if(columnId==null){
            return columns;
        }
        String[] columnIds=columnId.split("%");
        Arrays.stream(columnIds).forEach(column->{
            StringBuilder colTree=new StringBuilder();
            String[] cids=column.split("/");
            if(cids.length>1){
                DbLibrary lib=dbLibService.getById(cids[0]);
                colTree.append(lib.getName());
            }
            if(cids.length>=2){
                List<DbColumn> dbColumnList=dbColumnService.selectParents(Integer.parseInt(cids[cids.length-1]));
                if(dbColumnList.size()>0){
                    for(DbColumn dbColumn:dbColumnList){
                        if(dbColumn!=null){
                            colTree.append("-");
                            colTree.append(dbColumn.getName());
                        }
                    }
                }
            }
            columns.add(colTree.toString());
        });
        return columns;
    }

    private void updateSolr(LocalChronicle localChronicle){
        try {
            HttpSolrClient client = new HttpSolrClient.Builder("http://solr.rd.wanfangdata.com.cn/solr/LocalChronicleCPC20200908")
                    .withConnectionTimeout(10000)
                    .withSocketTimeout(60000)
                    .build();
            SolrInputDocument document = new SolrInputDocument();
            document.addField("Id",localChronicle.getId());
            document.addField("Title",localChronicle.getNewTitle());
            UpdateResponse ur=solrClient.add(document);
            client.commit();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void updateItemSolr(LocalChronicleItem item){
        try {
            HttpSolrClient client = new HttpSolrClient.Builder("http://solr.rd.wanfangdata.com.cn/solr/LocalChronicleItemCPC20201010")
                    .withConnectionTimeout(10000)
                    .withSocketTimeout(60000)
                    .build();
            SolrInputDocument document = new SolrInputDocument();
            document.addField("Id",item.getId());
            document.addField("Title",item.getNewTitle());
            client.add(document);
            client.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
