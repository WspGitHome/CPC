package com.wanfangdata.grpc.server.query.chain.util;

import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.*;

/**
 *条目排序  获取目录
 * @author FLY
 * @date 2019-11-1
 *
 * */
public class ComparatorItem implements Comparator {
    private static Logger logger = LoggerFactory.getLogger(ComparatorItem.class);

    @Override
    public int compare(Object object1, Object object2) {
        Map<String,String> cw1 = (Map) object1;
        Map<String,String> cw2 = (Map) object2;
        //默认flag值1，默认是升序，如果返回是为-flag则是降序
        String No1 = StringUtil.nvl(cw1.get(Constant.NO));
        String No2 = StringUtil.nvl(cw2.get(Constant.NO));
        return No1.compareTo(No2);
    }

    public Catalog<Map> getCatalogTree(List<SolrDocument> solrDocumentList){
        Catalog catalog=new Catalog();
        if(solrDocumentList.isEmpty()){
            return catalog;
        }
        Collections.sort(solrDocumentList,this);
        List<Catalog<Map>> treeFirstLevelList=new ArrayList<>();
        //第一级目录
        for(SolrDocument map:solrDocumentList){
            String realLevel=StringUtil.nvl(map.get(Constant.RealLevel));
            if("1".equals(realLevel)){
                Catalog catalog1=new Catalog();
                catalog1.setItem(map);
                treeFirstLevelList.add(getSubCatalogTree(catalog1,solrDocumentList));
            }
        }
        catalog.setSubItemList(treeFirstLevelList);
        return catalog;
    }
    /**
     * 获取子目录
     * */
    public Catalog<Map> getSubCatalogTree(Catalog<Map> catalog1,List<SolrDocument> result){
        if(catalog1.getItem()==null){
            return null;
        }
        List<Catalog<Map>> list=new ArrayList<>();
        String id=StringUtil.nvl(catalog1.getItem().get(Constant.ID));
        for(SolrDocument subMap:result) {
            String subId = StringUtil.nvl(subMap.get(Constant.ParentID));
            if(!subId.equals("")&&id.equals(subId)){
                Catalog catalog2=new Catalog();
                catalog2.setItem(subMap);
                Catalog<Map> subCatalog=getSubCatalogTree(catalog2,result);
                if(subCatalog!=null){
                    list.add(subCatalog);
                }
            }
        }
        catalog1.setSubItemList(list);
        return catalog1;
    }
    /**
     * 只需要保留前台需要参数
     * */
    private Map<String,String> reMap(Map<String,String> map){
        Map<String,String> reMap=new HashMap<String,String>();
        reMap.put(Constant.ID,map.get(Constant.ID));
        reMap.put(Constant.Title,map.get(Constant.Title));
        reMap.put(Constant.Page,map.get(Constant.Page));
        reMap.put(Constant.RealLevel,map.get(Constant.RealLevel));
        reMap.put(Constant.ParentID,map.get(Constant.ParentID));
        reMap.put(Constant.PageCount,map.get(Constant.PageCount));
        return reMap;
    }
}