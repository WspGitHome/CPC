package com.wanfangdata.cpc.service.impl;

import com.wanfangdata.cpc.service.CatalogueService;
import com.wanfangdata.cpc.utils.Constant;
import com.wanfangdata.cpc.search.utils.SearchUtil;
import com.wanfangdata.oss.model.BookInfo;
import com.wanfangdata.oss.model.CatalogueNode;
import com.wanfangdata.oss.model.VolumeNode;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 *@program: Fulltext
 *@description: 方志目录
 *@author: FLY
 *@create: 2020-05-06 14:52
 */
@Service
public class CatalogueServiceImpl implements CatalogueService {

    private static final Logger log = LoggerFactory.getLogger(CatalogueServiceImpl.class);


    @Value("${solr.collection.localChronicleItem}")
    private String  localChronicleItem;
    @Autowired
    private SolrClient solrClient;

    @Override
    @Cacheable(value = "catalogue",key="#id",unless = "#result.size()==0")
    public List<CatalogueNode> catalogue(String id)  throws Exception {
        SolrQuery params = new SolrQuery();
        params.setQuery(Constant.BookID+":"+id)
//                .addFilterQuery("RealLevel:[1 TO 3]")
                .setRows(Constant.ROWS)
                .setParam("fl",String.join(",", Constant.catalogueReturnedField));
        try{
            QueryResponse response = solrClient.query(localChronicleItem,params);
            SolrDocumentList docs=response.getResults();
            List<CatalogueNode> list= SearchUtil.buildTreeParallel(docs);
            log.info("获取书籍目录 id:{} 目录size:{}",id,list.size());
            return list;
        }catch (Exception e){
            log.error("获取目录出错 id:{} 原因:{}",id,e.getMessage());
            return new ArrayList<>();
        }

    }


    @Override
    @Cacheable(value = "volumes",key="#id",unless = "#result.size()==0")
    public List<VolumeNode> volumes(String id)  throws Exception {
        SolrQuery params = new SolrQuery();
        params.setQuery(Constant.BookID+":"+id)
                .setRows(Constant.ROWS)
                .setParam("fl",String.join(",", Constant.volumeReturnedField));
        try{
            QueryResponse response = solrClient.query(localChronicleItem,params);
            SolrDocumentList docs=response.getResults();

            List<VolumeNode> list=new ArrayList<>();
            docs.stream().forEach(d->{
                VolumeNode volumeNode=new VolumeNode();
                volumeNode.setId(String.valueOf(d.get(Constant.ID)));
                volumeNode.setTitle(String.valueOf(d.get(Constant.Title)));
                volumeNode.setNo(String.valueOf(d.get(Constant.NO)));
                volumeNode.setPageCount(String.valueOf(d.get(Constant.PageCount)));
                volumeNode.setVolumeSort(String.valueOf(d.get(Constant.VolumeSort)));
                list.add(volumeNode);
            });
            list.sort(Comparator.comparing(VolumeNode::getVolumeSort));
            log.info("获取分卷目录 id:{} 目录size:{}",id,list.size());
            return list;
        }catch (Exception e){
            log.error("获取分卷出错 id:{} 原因:{}",id,e.getMessage());
            return new ArrayList<>();
        }

    }

}