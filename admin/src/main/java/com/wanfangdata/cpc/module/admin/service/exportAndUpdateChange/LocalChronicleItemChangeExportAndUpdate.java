package com.wanfangdata.cpc.module.admin.service.exportAndUpdateChange;

/**
 * @Packagename com.wanfangdata.cpc.module.admin.service.exportAndUpdateChange
 * @Classname LocalChronicleItemChangeExportAndUpdate
 * @Description
 * @Authors Mr.Wu
 * @Date 2020/08/21 09:27
 * @Version 1.0
 */
public class LocalChronicleItemChangeExportAndUpdate extends ChangeExportAndUpdateFactory  {
    public LocalChronicleItemChangeExportAndUpdate(String dbType, String startTime, String endTime) {

        super(dbType, startTime, endTime);
    }

    @Override
    public byte[] getExportStream() {
        return new byte[0];
    }

    @Override
    public void solrChange() {
        System.out.println("I am B"+this.dbType + "->startTime:" + this.startTime + "endTime:" + this.endTime);
    }
}
