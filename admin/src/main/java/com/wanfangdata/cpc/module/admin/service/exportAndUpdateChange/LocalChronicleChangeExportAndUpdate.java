package com.wanfangdata.cpc.module.admin.service.exportAndUpdateChange;

import com.wanfangdata.cpc.common.util.SpringUtils;
import com.wanfangdata.cpc.module.admin.mapper.db.DbPropertyModifyMapper;
import com.wanfangdata.cpc.module.admin.model.db.DbLibrary;
import com.wanfangdata.cpc.module.admin.service.db.DbLibService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Packagename com.wanfangdata.cpc.module.admin.service.exportAndUpdateChange
 * @Classname LocalChronicleChangeExportAndUpdate
 * @Description
 * @Authors Mr.Wu
 * @Date 2020/08/21 09:26
 * @Version 1.0
 */
public class LocalChronicleChangeExportAndUpdate extends ChangeExportAndUpdateFactory {

    public LocalChronicleChangeExportAndUpdate(String dbType, String startTime, String endTime) {
        super(dbType, startTime, endTime);
    }

    @Override
    public byte[] getExportStream() {
        return new byte[0];
    }

    @Override
    public void solrChange() {
        DbPropertyModifyMapper dbPropertyModifyMapper = (DbPropertyModifyMapper) SpringUtils.getBean("dbPropertyModifyMapper");
        System.out.println(this.dbType + "->startTime:" + this.startTime + "endTime:" + this.endTime);
        SolrInputDocument document = new SolrInputDocument();
        String id = "xxx", fieldValue = "xxx";
        document.addField("id", id);
        Map<String, Object> warningData = new HashMap<>();
        warningData.put("set", fieldValue);
        document.addField("SolrField", warningData);
    }

    private void solrCommit(String DEFAULT_COLLECTION_NAME, SolrInputDocument solrInputFields) {
        SolrClient solrClient = (SolrClient) SpringUtils.getBean("solrClient");

        try {
            solrClient.add(DEFAULT_COLLECTION_NAME, solrInputFields);
            solrClient.commit(DEFAULT_COLLECTION_NAME);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
