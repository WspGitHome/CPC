package com.wanfangdata.cpc.module.admin.importer.mysql;

import com.wanfangdata.cpc.module.admin.importer.StringUtil;
import com.wanfangdata.cpc.module.admin.importer.TotalModel;
import com.wanfangdata.cpc.module.admin.importer.Watcher;
import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *@program: LocalChronicleGrpcSearch88882
 *@description: 读取access
 *@author: FLY
 *@create: 2020-08-06 08:54
 */
public class ExcelToMysql {

    private static Integer pageSize=1;

    private static final String XLS = "xls";
    private static final String XLSX = "xlsx";

    public static void importer(String absolutePath,Watcher watcher) throws Exception {
        Workbook workbook = null;
        String fileType = absolutePath.substring(absolutePath.lastIndexOf(".") + 1, absolutePath.length());
        if (fileType.equalsIgnoreCase(XLS)) {
            workbook = new HSSFWorkbook(new FileInputStream(absolutePath));
        } else if (fileType.equalsIgnoreCase(XLSX)) {
            workbook = new XSSFWorkbook(new FileInputStream(absolutePath));
        }
        setTotal(workbook,watcher);
        try {
            for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
                Sheet sheet = workbook.getSheetAt(sheetNum);
                Row frow = sheet.getRow(0);
                //解析values
                List<Map<String, Object>> list = new ArrayList<>(pageSize);
                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    Map<String, Object> value = new HashedMap();
                    for (int j = 0; j < row.getLastCellNum(); j++) {
                        Cell feild=frow.getCell(j);
                        Cell cell = row.getCell(j);
                        if(cell==null||feild==null){
                            continue;
                        }
                        value.put(getCellValueByCell(feild), getCellValueByCell(cell));
                    }
                    list.add(value);
                    if (list.size() == pageSize) {
                        watcher.process(list);
                        list.clear();
                    }
                }
                if (list.size() > 0) {
                    watcher.process(list);
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            workbook.close();
        }
    }

    public static void setTotal( Workbook workbook,Watcher watcher) {
        TotalModel totalModel =new TotalModel();
        for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
            Sheet sheet = workbook.getSheetAt(sheetNum);
            totalModel.add(sheet.getLastRowNum());
            watcher.setDataModel(totalModel);
        }
    }

    /**
     * 获取单元格各类型值，返回字符串类型
     * */
    private static String getCellValueByCell(Cell cell) {
        //判断是否为null或空串
        if (cell==null || cell.toString().trim().equals("")) {
            return "";
        }
        String cellValue = "";

        switch (cell.getCellType()) {
            case STRING: //字符串类型
                cellValue= StringUtil.nvl(cell.getStringCellValue());
                break;
            case BOOLEAN:  //布尔类型
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case NUMERIC: //数值类型
                if (HSSFDateUtil.isCellDateFormatted(cell)) {  //判断日期类型
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    cellValue = simpleDateFormat.format(cell.getDateCellValue());
                } else {  //否
                    cellValue = new DecimalFormat("#.######").format(cell.getNumericCellValue());
                }
                break;
            default: //其它类型，取空串吧
                cellValue = "";
                break;
        }
        return cellValue;
    }

}