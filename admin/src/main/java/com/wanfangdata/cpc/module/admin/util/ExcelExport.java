package com.wanfangdata.cpc.module.admin.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @ProjectName: LocalChronicleGrpcSearch
 * @Package: com.wanfangdata.cpc.module.admin.util
 * @ClassName: ExcelExport
 * @Description: 描述类
 * @Author: rongrong
 * @CreateDate: 2020/8/24
 * @Version: 1.0
 */
public class ExcelExport {
    protected final static Logger logger = LoggerFactory.getLogger(ExcelExport.class);
    /*** 
     * @description
     * @param response 返回的HttpServletResponse
     * @param importlist 要导出的对象的集合
     * @param columnList 表头
     * @return void
     * @authors rongrong
     * @date 2020/8/24
     * @modified by
     * @version 1.0
     */
    public static void export(HttpServletResponse response, List<?> importlist, List<String> columnList,String proType) {
        //获取数据集
        List<?> datalist = importlist;
        //声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        //生成一个表格
        HSSFSheet sheet = workbook.createSheet();
        //设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 18);
        //获取对象属性
        Field[] fields = ClassUtil.getClassAttribute(importlist.get(0));
        //获取对象get方法
        List<Method> methodList = ClassUtil.getMethodGet(importlist.get(0));

        //循环字段名数组，创建标题行
        Row row = sheet.createRow(0);
        for (int j = 0; j < columnList.size(); j++) {
            //创建列
            Cell cell = row.createCell(j);
            //设置单元类型为String
            cell.setCellType(CellType.STRING);
            cell.setCellValue(columnList.get(j));
        }
        //创建普通行
        for (int i = 0; i < datalist.size(); i++) {
            //因为第一行已经用于创建标题行，故从第二行开始创建
            row = sheet.createRow(i + 1);
            //如果是第一行就让其为标题行
            Object targetObj = datalist.get(i);
            for (int j = 0; j < fields.length; j++) {
                //创建列
                Cell cell = row.createCell(j);
                cell.setCellType(CellType.STRING);
                //
                try {
                    Object value = methodList.get(j).invoke(targetObj, new Object[]{});
                    cell.setCellValue(transCellType(value));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        //浏览器下载excel
        buildExcelDocument(proType+".xlsx",workbook,response);
    }

    private static String transCellType(Object value) {
        String str = null;
        if (value instanceof Date) {
            Date date = (Date) value;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            str = sdf.format(date);
        } else {
            str = String.valueOf(value);
            if (str == "null") {
                str = "";
            }
        }

        return str;
    }

    private static  void  buildExcelDocument(String fileName, Workbook wb, HttpServletResponse response){
        try {
             response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            //让浏览器下载文件,name是上述默认文件下载名
            response.addHeader("Content-Disposition","attachment;filename=\"" + fileName + "\"");
            response.flushBuffer();
            wb.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
