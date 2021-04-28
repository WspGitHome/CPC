package com.wanfangdata.cpc.search.utils;

import com.wanfangdata.cpc.utils.TreeNode;

import java.util.*;


public class ItemTree {
    //新志条目目录
    public static TreeNode getNavigateTree(Map<String,String> dic, List<Map<String,String>> lists) {
        //志书根节点
        TreeNode root = new TreeNode();
        root.setKey(dic.get("BookTitle").toString());
        root.setValue(dic.get("BookID").toString());
        TreeNode node = root;
        //添加当前节点之前的目录节点
        Object sheet = dic.get("Sheet");
        Object sheetInfo = dic.get("SheetInfo");
        if (sheet != null && sheetInfo != null)//篇
        {
            node.getNodeList().add(new TreeNode(sheet.toString(), sheetInfo.toString()));
            node = node.getNodeList().get(0);
        }
        Object chapter = dic.get("Chapter");
        Object chapterInfo = dic.get("ChapterInfo");
        if (chapter != null && chapterInfo != null)//章
        {
            node.getNodeList().add(new TreeNode(chapter.toString(), chapterInfo.toString()));
            node = node.getNodeList().get(0);
        }
        Object section = dic.get("Section");
        Object sectionInfo = dic.get("SectionInfo");
        if (section != null && sectionInfo != null)//节
        {
            node.getNodeList().add(new TreeNode(section.toString(), sectionInfo.toString()));
            node = node.getNodeList().get(0);
        }
        Object item = dic.get("Item");
        Object itemInfo = dic.get("ItemInfo");
        if (item != null && itemInfo != null)//目
        {
            node.getNodeList().add(new TreeNode(item.toString(), itemInfo.toString()));
            node = node.getNodeList().get(0);
        }
        Object subItem = dic.get("SubItem");
        Object subItemInfo = dic.get("SubItemInfo");
        if (subItem != null && subItemInfo != null)//子目
        {
            node.getNodeList().add(new TreeNode(subItem.toString(), subItemInfo.toString()));
            node = node.getNodeList().get(0);
        }
        //添加当前节点
        TreeNode current = new TreeNode(dic.get("Title").toString(), dic.get("Id").toString());
        current.setRoot(true);
        node.getNodeList().add(current);
        // 添加当前节点的子目录节点
        if (lists != null && lists.size() != 0) {
            for (int i = 0; i < lists.size(); i++) {
                TreeNode tree = new TreeNode(lists.get(i).get("Title").toString(), lists.get(i).get("Id").toString());
                current.getNodeList().add(tree);
            }
        }
        return root;
    }

}