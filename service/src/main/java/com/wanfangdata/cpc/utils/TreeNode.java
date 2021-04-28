package com.wanfangdata.cpc.utils;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * Created on 2017/1/4.
 *
 * @author meibr
 */
public class TreeNode {
    private static Logger log = Logger.getLogger(TreeNode.class);
    private String key;
    private String value;
    private Integer count=0;
    private List<TreeNode> nodeList = new ArrayList<>();
    private boolean isRoot;

    public TreeNode(String xmlName) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        Document doc = null;
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(xmlName + ".xml");
        try {
            builder = factory.newDocumentBuilder();
            doc = builder.parse(in);
        } catch (Exception e) {
            log.error("加载xml文件失败：" + xmlName + ".xml", e);
            throw e;
        }finally {
            in.close();
        }
        Element root = doc.getDocumentElement();
        NodeList childNodes = root.getChildNodes();
        this.isRoot = true;
        if (childNodes.getLength() == 0) {
            return;
        }
        this.nodeList = new ArrayList<>();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Element node = null;
            try {
                node = (Element) childNodes.item(i);
            } catch (ClassCastException e) {
                continue;
            }
            this.nodeList.add(new TreeNode(node, this));
        }
    }

    public TreeNode(Element node, TreeNode fatherNode) {
        this.isRoot = false;
        NodeList childNodes = node.getChildNodes();
        this.key = node.getAttribute("Name");
        value = node.getAttribute("Value");
        if (childNodes.getLength() == 0) {
            return;
        }
        this.nodeList = new ArrayList<>();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Element childNode = null;
            try {
                childNode = (Element) childNodes.item(i);
            } catch (ClassCastException e) {
                continue;
            }
            this.nodeList.add(new TreeNode(childNode, this));
        }
    }

    public TreeNode() {
        super();
    }

    public TreeNode(String text, String id)
    {
        key = text;
        value = id;
        isRoot = isRoot();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<TreeNode> getNodeList() {
        return nodeList;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setNodeList(List<TreeNode> nodeList) {
        this.nodeList = nodeList;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public void setRoot(boolean root) {
        isRoot = root;
    }

    @Override
    public TreeNode clone() throws CloneNotSupportedException {
        TreeNode treeNode=new TreeNode();
        treeNode.setKey(key);
        treeNode.setValue(value);
        treeNode.setCount(count);
        treeNode.setRoot(isRoot);
        if(nodeList!=null&&nodeList.size()>0){
            List<TreeNode> list = new ArrayList<>();
            for(TreeNode node:nodeList){
                list.add(node.clone());
            }
            treeNode.setNodeList(list);
        }
        return treeNode;
    }
}
