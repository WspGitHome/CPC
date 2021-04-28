package com.wanfangdata.cpc.search.utils;


import com.wanfangdata.cpc.utils.Constant;
import com.wanfangdata.cpc.utils.StringUtil;
import com.wanfangdata.oss.model.BookInfo;
import com.wanfangdata.oss.model.CatalogueNode;
import org.apache.solr.common.SolrDocumentList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 *@program: catalogue
 *@description: 生成目录树
 *@author: FLY
 *@create: 2020-05-06 10:57
 */
public class SearchUtil {


    /**
     *
     * 构建目录，根据CatalogueNode语义，目录有误，请检查此方法
     *
     *
     * */
    public static List<CatalogueNode> buildTreeParallel(SolrDocumentList docs){
        List<CatalogueNode> result = new ArrayList<>();
        List<CatalogueNode> list = new ArrayList<>();
        BookInfo bookInfo=new BookInfo();//辅助生成前端条目首页码
        docs.forEach(e -> {
            CatalogueNode catalogueNode=new CatalogueNode();
            catalogueNode.setId(String.valueOf(e.get(Constant.ID)));
            catalogueNode.setTitle(String.valueOf(e.get(Constant.Title)));
            catalogueNode.setParentID(String.valueOf(e.get(Constant.ParentID)));
            catalogueNode.setBookID(String.valueOf(e.get(Constant.BookID)));

            String page=String.valueOf(e.get(Constant.Page));
            String[] pages=page.split("-");
            String beginPara="0";
            String endPara="0";
            if(pages.length==2){
                beginPara=page.split("-")[0];
                endPara=page.split("-")[1];
            }
            catalogueNode.setBeginPara(beginPara);
            //给其默认值
            catalogueNode.setStartPara(beginPara);
            catalogueNode.setEndPara(endPara);
            //给其默认值
            catalogueNode.setFinishPara(endPara);

            catalogueNode.setPage(String.valueOf(e.get(Constant.Page)));
            catalogueNode.setNo(String.valueOf(e.get(Constant.NO)));
            catalogueNode.setLocation(String.valueOf(e.get(Constant.Location)));

            bookInfo.setBookInfo(catalogueNode.getLocation(),catalogueNode.getEndPara());
            list.add(catalogueNode);
        });

        final boolean offset=bookInfo.getOffset()!=0;
        list.sort(new Comparator<CatalogueNode>() {
            @Override
            public int compare(CatalogueNode o1, CatalogueNode o2) {
                String No1 = StringUtil.nvl(o1.getNo());
                String No2 = StringUtil.nvl(o2.getNo());
                int flag = No1.compareTo(No2);
                return flag;
            }
        });
        list.forEach(catalogueNode -> {
            //真实页码设置开始 只有02才需要偏移
            if (offset && catalogueNode.getLocation().indexOf("_02") != -1) {
                String startPara = catalogueNode.getStartPara();
                String finishPara = catalogueNode.getFinishPara();
                int start = StringUtil.parseInt(startPara, 0) + bookInfo.getOffset();
                int finish = StringUtil.parseInt(finishPara, 0) + bookInfo.getOffset();
                catalogueNode.setStartPara(String.valueOf(start));
                catalogueNode.setFinishPara(String.valueOf(finish));
            }

            if ("null".equals(catalogueNode.getParentID())||"0".equals(catalogueNode.getParentID())) {
                result.add(findChildren(catalogueNode,list));
            }

        });
        return result;
    }

    /**
     * 递归查找子节点
     * @param catalogueNode catalogueNodes
     * @return
     */
    public static CatalogueNode findChildren(CatalogueNode catalogueNode, List<CatalogueNode> catalogueNodes) {
        for (CatalogueNode it : catalogueNodes) {
            if(catalogueNode.getId().equals(it.getParentID())) {
                if (catalogueNode.getCatalogueNodes() == null) {
                    catalogueNode.setCatalogueNodes(new ArrayList<CatalogueNode>());
                }
                catalogueNode.getCatalogueNodes().add(findChildren(it,catalogueNodes));
            }
        }
        return catalogueNode;
    }



}