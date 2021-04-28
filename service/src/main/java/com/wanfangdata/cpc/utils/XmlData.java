package com.wanfangdata.cpc.utils;

//import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * Created on 2017/1/4.
 *
 * @author meibr
 */
public class XmlData {
   // private static Logger log = Logger.getLogger(XmlData.class);
    private Map<String, TreeNode> tree;
    private Map<String, Map<String, String>> map;
    private Map<String,String> codeMap;
    private Map<String,String> nameMap;
    private static final XmlData data = new XmlData();

    private XmlData() {
    }

    /**
     * 获取xmlData单例
     *
     * @return
     */
    public static XmlData getInstance() {
        return data;
    }

    /**
     * 获取key-value xml树形结构
     *
     * @param xmlName xml文件名（传值不含后缀，但目标文件后缀必须为.xml）
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public TreeNode getTree(String xmlName) throws ParserConfigurationException, SAXException, IOException {
        if (tree != null && tree.containsKey(xmlName)) {
            return tree.get(xmlName);
        }
        tree = new HashMap<>();
        TreeNode treeNode = null;
        try {
            treeNode = new TreeNode(xmlName);
        } catch (Exception e) {
            throw e;
        }
        tree.put(xmlName, treeNode);
        return treeNode;
    }

    public Map<String, String> getMap(String xmlName,String tagName){
        if (map != null && map.containsKey(xmlName+tagName)) {
            return map.get(xmlName+tagName);
        }
        if(null == map){
            map = new HashMap<>();
        }
        Map<String, String> resultMap = new HashMap<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        Document doc = null;
        String path = xmlName + ".xml";
        try (InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path)) {
            builder = factory.newDocumentBuilder();
            doc = builder.parse(stream);
        } catch (Exception e) {

        }
        Element root = doc.getDocumentElement();
        NodeList childNodes = root.getElementsByTagName(tagName);
        for (int i = 0; i < childNodes.getLength(); i++) {
            Element node = (Element) childNodes.item(i);
            String key = node.getAttribute("Name");
            String value = node.getAttribute("Value");
            resultMap.put(key, value);
        }
        map.put(xmlName+tagName, resultMap);
        return resultMap;
    }
    /**
     *
     *中文名到代码映射
     * */
    public String getCodeMap(String xmlName,String category) {
        String code = "M";
        try {
            if (category == "") {
                return code;
            }
            if(codeMap!=null){
                return codeMap.get(category);
            }
            codeMap=new HashMap<>();
            TreeNode tree = XmlData.getInstance().getTree(xmlName);
            List<TreeNode> nodeList = tree.getNodeList();
            for (TreeNode node : nodeList) {
                codeMap.put(node.getKey(),"0/"+node.getValue());
                //暂时只支持一维聚类
               /* List<TreeNode> subnodeList=node.getNodeList();
                for (TreeNode subnode : subnodeList) {
                    codeMap.put(subnode.getKey(),"1/"+node.getValue()+"/"+subnode.getValue());
                }*/
            }
            return codeMap.get(category);
        } catch (Exception e) {

        }
        return code;
   }
    /**
     *
     *代码到中文名映射
     * */
    public String getNameMap(String xmlName,String code) {
        String name = "其他";
        try {
            if (code == "") {
                return code;
            }
            if(nameMap!=null){
                return nameMap.get(code);
            }
            nameMap=new HashMap<>();
            TreeNode tree = XmlData.getInstance().getTree(xmlName);
            List<TreeNode> nodeList = tree.getNodeList();
            for (TreeNode node : nodeList) {
                nameMap.put(node.getValue(),node.getKey());
                if( node.getNodeList()!=null) {
                    List<TreeNode> subnodeList = node.getNodeList();
                    for (TreeNode subnode : subnodeList) {
                        nameMap.put(subnode.getValue(), subnode.getKey());
                        if (subnode.getNodeList() != null) {
                            for (TreeNode grantnode : subnode.getNodeList()) {
                                nameMap.put(grantnode.getValue(), grantnode.getKey());
                            }
                        }
                    }
                }
            }
            return nameMap.get(code);
        } catch (Exception e) {

        }
        return code;
    }
    /**
     *
     * 根据分类专辑编号查找分类专辑名称
     * */
    public String getAlbumCategoryFromCode(String code) {
        try {
            Map<String, String> map = XmlData.getInstance().getMap("albumCategory", "AlbumCategory");
            if (map.get(code) != null) {
                return map.get(code);
            }
            Map<String, String> submap = XmlData.getInstance().getMap("albumCategory", "SubAlbumCategory");
            if (map.get(code) != null) {
                return submap.get(code);
            }
        } catch (Exception e) {
        }
        return "";
    }

    public static void main(String[] args){
        try {
            Map<String, String> map = XmlData.getInstance().getMap("albumCategory", "AlbumCategory");
            Map<String, String> submap = XmlData.getInstance().getMap("albumCategory", "SubAlbumCategory");

            TreeNode node=XmlData.getInstance().getTree("albumCategory");
            int i=1;
        }catch (Exception e){

        }
    }
}
