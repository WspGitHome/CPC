package com.wanfangdata.cpc.module.admin.service.exportAndUpdateChange;


/**
 * @Packagename com.wanfangdata.cpc.module.admin.service.exportAndUpdateChange
 * @Classname ChangeExportAndUpdateFactory
 * @Description
 * @Authors Mr.Wu
 * @Date 2020/08/21 09:29
 * @Version 1.0
 */
public class ChangeExportAndUpdateFactory {
    public String dbType;
    public String startTime;

    public byte[] getExportStream() {
        return null;
    }

    public void solrChange() {

    }

    public ChangeExportAndUpdateFactory(String dbType, String startTime, String endTime) {
        this.dbType = dbType;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String endTime;

    public ChangeExportAndUpdateFactory getChangeExportAndUpdate() {
        if (dbType.equals("A")) {
            return new LocalChronicleChangeExportAndUpdate(dbType, startTime, endTime);
        } else if (dbType.equals("B")) {
            return new LocalChronicleItemChangeExportAndUpdate(dbType, startTime, endTime);
        }
        return null;
    }


}