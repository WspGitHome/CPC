package com.wanfangdata.cpc.module.admin.importer.mysql;

import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Row;
import com.healthmarketscience.jackcess.Table;
import com.wanfangdata.cpc.module.admin.importer.TotalModel;
import com.wanfangdata.cpc.module.admin.importer.Watcher;

import java.io.File;
import java.util.*;

/**
 *@program: LocalChronicleGrpcSearch88882
 *@description: 读取access
 *@author: FLY
 *@create: 2020-08-06 08:54
 */
public class AccessToMysql {

    private static Integer pageSize=1;

    static {
        try{
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void importer(String absolutePath,Watcher watcher) throws Exception {
        Database db = DatabaseBuilder.open(new File(absolutePath));
        setTotal(db,watcher);
        try {
            Set<String> tables = db.getTableNames();
            for (String table : tables) {
                Table t = db.getTable(table);
                Iterator<Row> it = t.iterator();
                List<Map<String, Object>> list = new ArrayList<>(pageSize);
                while (it.hasNext()) {
                    Row row = it.next();
                    list.add(row);
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
            db.close();
        }
    }

    public static void setTotal(Database db, Watcher watcher) throws Exception{
        TotalModel totalModel =new TotalModel();
        Set<String> tables = db.getTableNames();
        for (String table : tables) {
            Table t = db.getTable(table);
            totalModel.add(t.getRowCount());
            watcher.setDataModel(totalModel);
        }
    }
}