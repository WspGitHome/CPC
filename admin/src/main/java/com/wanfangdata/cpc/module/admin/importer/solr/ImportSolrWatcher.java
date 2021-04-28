package com.wanfangdata.cpc.module.admin.importer.solr;

import com.wanfangdata.cpc.module.admin.importer.Constant;
import com.wanfangdata.cpc.module.admin.importer.ImportException;
import com.wanfangdata.cpc.module.admin.importer.TotalModel;
import com.wanfangdata.cpc.module.admin.importer.Watcher;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.common.SolrInputDocument;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *@program: LocalChronicleGrpcSearch88882
 *@description: 记录出错情况并通知到前台
 *@author: FLY
 *@create: 2020-08-06 08:58
 */
public class ImportSolrWatcher implements Watcher {

    private SimpleDateFormat simpleDateFormat;
    private static Integer MaxTolerate=200;
    private List<String> errorHolder=new ArrayList<>(MaxTolerate);
    private TotalModel totalModel =new TotalModel();
    private final SolrClient solrClient;
    private final String propertySolrAlias;

    public ImportSolrWatcher(String propertySolrAlias, SolrClient solrClient){
        simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        this.solrClient=solrClient;
        this.propertySolrAlias=propertySolrAlias;
    }
    @Override
    public void recodeError(String error) {
        if(errorHolder.size()<MaxTolerate){
            errorHolder.add(error);
        }
    }

    @Override
    public void notifier() {

    }

    @Override
    public void process(List<Map<String, Object>> values)  throws ImportException {
        List<SolrInputDocument> documents=new ArrayList<>();
        values.stream().forEach(map->{
            map.remove(Constant.CreateTime);
            map.remove(Constant.UpdateTime);
            map.remove(Constant.BatchId);
            SolrInputDocument document = new SolrInputDocument();
            for (Map.Entry<String, Object> entry:map.entrySet() ) {
                String key=entry.getKey();
                Object value=entry.getValue();
                if(value!=null&&!value.toString().equals("")){
                    if(value instanceof Date){
                        document.addField(entry.getKey(),simpleDateFormat.format(value));
                        continue;
                    }else{
                        String val=String.valueOf(entry.getValue());
                        if(Constant.isDateType(key)){
                            if(val.length()==19){
                                val=val.replace(" ","T").concat("Z");
                                document.addField(entry.getKey(),val);
                            }
                            if(val.length()==10){
                                val=val+"T00:00:00Z";
                                document.addField(entry.getKey(),val);
                            }
                            continue;
                        }if(Constant.LibraryId.equals(key)||Constant.ColumnId.equals(key)){
                            document.addField(entry.getKey(), StringUtils.split(String.valueOf(entry.getValue()), Constant.separator));
                            continue;
                        }else if(val.contains(Constant.separator_nomal)){
                            document.addField(entry.getKey(), StringUtils.split(String.valueOf(entry.getValue()), Constant.separator_nomal));
                            continue;
                        }else{
                            document.addField(entry.getKey(),val);
                        }
                    }
                }
            }
            try {
                documents.add(document);
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        try {
            solrClient.add(propertySolrAlias,documents);
        }catch (Exception e){
            recodeError("solr导入失败:"+e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void setDataModel(TotalModel data) {
        this.totalModel=data;
    }
}