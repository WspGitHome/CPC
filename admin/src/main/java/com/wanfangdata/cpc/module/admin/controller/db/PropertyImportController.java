package com.wanfangdata.cpc.module.admin.controller.db;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wanfangdata.cpc.common.config.properties.FileUploadProperties;
import com.wanfangdata.cpc.common.util.CoreConst;
import com.wanfangdata.cpc.common.util.Pagination;
import com.wanfangdata.cpc.common.util.ResultUtil;
import com.wanfangdata.cpc.enums.PropertyStatus;
import com.wanfangdata.cpc.module.admin.importer.Constant;
import com.wanfangdata.cpc.module.admin.importer.NormalTranslater;
import com.wanfangdata.cpc.module.admin.importer.Translater;
import com.wanfangdata.cpc.module.admin.importer.mysql.AccessToMysql;
import com.wanfangdata.cpc.module.admin.importer.mysql.ExcelToMysql;
import com.wanfangdata.cpc.module.admin.importer.mysql.ImportMysqlWatcher;
import com.wanfangdata.cpc.module.admin.importer.solr.ImportSolrWatcher;
import com.wanfangdata.cpc.module.admin.importer.solr.MysqlToSolr;
import com.wanfangdata.cpc.module.admin.model.db.DbColumn;
import com.wanfangdata.cpc.module.admin.model.db.DbLibtype;
import com.wanfangdata.cpc.module.admin.model.db.DbProperty;
import com.wanfangdata.cpc.module.admin.model.db.DbPropertyImport;
import com.wanfangdata.cpc.module.admin.service.db.DbColumnService;
import com.wanfangdata.cpc.module.admin.service.db.DbLibService;
import com.wanfangdata.cpc.module.admin.service.db.PropertyImportService;
import com.wanfangdata.cpc.module.admin.service.db.PropertyService;
import com.wanfangdata.cpc.module.admin.vo.base.PageResultVo;
import com.wanfangdata.cpc.module.admin.vo.base.ResponseVo;
import com.wanfangdata.cpc.module.admin.vo.db.PropertyImportConditionVo;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.solr.client.solrj.SolrClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 后台类目管理
 *
 * @author Linzhaoguan
 * @version V1.0
 * @date 2019年9月11日
 */
@Controller
@RequestMapping("property/import/")
@AllArgsConstructor
public class PropertyImportController {

    private final FileUploadProperties fileUploadProperties;
    private final PropertyService propertyService;
    private final DbLibService dbLibService;
    private final PropertyImportService propertyImportService;
    private final DbColumnService dbColumnService;
    private final SolrClient solrClient;
    @PostMapping("list")
    @ResponseBody
    public PageResultVo propertyImport(PropertyImportConditionVo propertyImportConditionVo, Integer pageNumber, Integer pageSize) {
        IPage<DbPropertyImport> page = new Pagination<>(pageNumber, pageSize);
        List<DbPropertyImport> list = propertyImportService.findByCondition(page, propertyImportConditionVo);
        return  ResultUtil.table(list, page.getTotal());
    }
    /**
     *
     * 资源上传
     * */
    @GetMapping("/add")
    public String add(Model model) {
        List<DbLibtype> libraries=dbLibService.selecLibtypes();
        model.addAttribute("libraries", libraries);
        List<DbProperty> properties=propertyService.list();
        model.addAttribute("properties", properties);
        return CoreConst.ADMIN_PREFIX + "property/import/add";
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseVo add(DbPropertyImport dbPropertyImport) {
        Date date = new Date();
        dbPropertyImport.setCreateTime(date);
        dbPropertyImport.setUpdateTime(date);
        dbPropertyImport.setStatus(PropertyStatus.INIT.getCode());
        boolean i = propertyImportService.save(dbPropertyImport);
        if (i) {
            return ResultUtil.success("资源上传成功");
        } else {
            return ResultUtil.error("资源上传失败");
        }
    }

    /**
     *
     * 资源标引导入数据库
     * */
    @PostMapping("/database")
    @ResponseBody
    public ResponseVo database(Integer id) {
        DbPropertyImport dbPropertyImport=propertyImportService.getById(id);
        String absolutePath=fileUploadProperties.getUploadFolder()+dbPropertyImport.getFilePath();
        DbColumn dbcolumnTree=dbColumnService.columnTree(dbPropertyImport.getLibraryId());
        Translater translater= new NormalTranslater(dbcolumnTree);
        try {
            PropertyImportConditionVo propertyImportConditionVo=new PropertyImportConditionVo();
            IPage<DbPropertyImport> page = new Pagination<>(1, 1);
            propertyImportConditionVo.setStatus(PropertyStatus.INDEXED.getCode());
            List<DbPropertyImport> list =propertyImportService.findByCondition(page,propertyImportConditionVo);
            if(list.size()>0){
                return  ResultUtil.error("请先上线已标引数据，以免覆盖");
            }
            File file=new File(absolutePath);
            if(!file.exists()){
                return  ResultUtil.error("文件导入失败，文件不存在 path："+absolutePath);
            }
            Date importDate=new Date();
            DbPropertyImport dbPropertyImportUpdate=new DbPropertyImport();
            dbPropertyImportUpdate.setId(id);
            dbPropertyImportUpdate.setUpdateTime(importDate);
            dbPropertyImportUpdate.setStatus(PropertyStatus.INDEXED.getCode());
            propertyImportService.updateById(dbPropertyImportUpdate);
            //更新时间
            dbPropertyImport.setUpdateTime(importDate);
            ImportMysqlWatcher watcher=new ImportMysqlWatcher(propertyService,translater,dbPropertyImport);
            if(absolutePath.endsWith(".mdb")){
                AccessToMysql.importer(absolutePath,watcher);
            }else if(absolutePath.endsWith(".xlsx")||absolutePath.endsWith(".xls")){
                ExcelToMysql.importer(absolutePath,watcher);
            }
            if(watcher.getErrorHolder().size()>0){
                return ResultUtil.success("文件导入完成",watcher.getErrorHolder().toString());
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error("文件导入失败");
        }

        return ResultUtil.success("文件导入成功");
    }
    /**
     *
     * 资源导入solr
     * */
    @PostMapping("/solr")
    @ResponseBody
    public ResponseVo solr(Integer id) {
        DbPropertyImport dbPropertyImport=propertyImportService.getById(id);
        DbProperty dbProperty=propertyService.getById(dbPropertyImport.getPropertyId());
        Date importDate=new Date();
        DbPropertyImport dbPropertyImportUpdate=new DbPropertyImport();
        dbPropertyImportUpdate.setId(id);
        dbPropertyImportUpdate.setUpdateTime(importDate);
        dbPropertyImportUpdate.setStatus(PropertyStatus.ONLINE.getCode());
        propertyImportService.updateById(dbPropertyImportUpdate);

        ImportSolrWatcher watcher=new ImportSolrWatcher(dbProperty.getPropertySolrAlias(),solrClient);
        try {
            MysqlToSolr.importer(dbPropertyImport,propertyService,watcher);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error("数据上线失败");
        }
        return ResultUtil.success("数据上线成功");
    }


    @GetMapping("/edit")
    public String edit(Model model, Integer id) {
        return CoreConst.ADMIN_PREFIX + "category/form";
    }

    @PostMapping("/edit")
    @ResponseBody
    public ResponseVo edit(DbProperty dbProperty) {
        return null;
    }

    @PostMapping("/delete")
    @ResponseBody
    public ResponseVo delete(Integer id) {
      return null;
    }

    @PostMapping("/batch/delete")
    @ResponseBody
    public ResponseVo deleteBatch(@RequestParam("ids[]") Integer[] ids) {
       return null;
    }

    /**
     * 下载excel模板
     * */
    @RequestMapping("/downExcel")
    @ResponseBody
    public void downExcel(HttpServletResponse response,Integer id) throws IOException {
        DbProperty property=propertyService.getById(id);
        List<Map<String ,Object>> listColumns=propertyService.selectSchama(property.getPropertyTableName());
        List<String> columns=new ArrayList<>();
        for(Map<String,Object> column:listColumns){
            columns.add(String.valueOf(column.get(Constant.FIELD)));
        }
        columns.remove(Constant.CreateTime);
        columns.remove(Constant.UpdateTime);
        columns.remove(Constant.ColumnId);
        columns.remove(Constant.LibraryId);
        columns.remove(Constant.BatchId);

        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("sheet");
        Row headRow = sheet.createRow(0); //第一行为头
        for (int i=0;i<columns.size();i++){  //遍历表头数据
            Cell cell = headRow.createCell(i);  //创建单元格
            cell.setCellValue(columns.get(i));  //设置值
        }
        response.setDateHeader("Expires", 0);
        response.setHeader("Content-disposition", String.format("attachment; filename="+property.getPropertyName()+".xlsx"));
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("\"application/msexcel");
        try (ServletOutputStream out = response.getOutputStream()) {
            wb.write(out);
            out.flush();
        }
    }
}
