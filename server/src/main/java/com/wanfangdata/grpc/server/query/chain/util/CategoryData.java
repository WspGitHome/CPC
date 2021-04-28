package com.wanfangdata.grpc.server.query.chain.util;

import com.wanfangdata.grpc.server.config.Config;
import com.wanfangdata.grpc.server.db.dbservice.DataService;
import com.wanfangdata.grpc.server.util.SpringContextUtil;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 读取专辑映射配置  代码和分类名双向映射
 *
 * @author FLY
 * @date 2019-11-1
 */
public class CategoryData {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Map<String, BiMap<String, String>> biMaps = new HashMap<>();
    private static final CategoryData data = new CategoryData();

    //配置文件名
//    public static final  String CATEGORY = "category";


    private CategoryData() {
    }

    public static CategoryData getInstance() {
        return data;
    }

    public boolean hasCategoryConfig(String xmlName) {
        if (biMaps.containsKey(xmlName)) {
            return true;
        }
        getBiMap(xmlName);
        return biMaps.containsKey(xmlName);
    }

    public String getCategoryByCode(String xmlName, String code) {
        StringBuilder name = new StringBuilder();
        if (StringUtils.isNotBlank(code)) {
            Integer dbId = Integer.valueOf(code.substring(0, code.indexOf("/")));
            String[] codeSplit = code.split("/");
            if (codeSplit.length >= 2) {
                String code1 = codeSplit.length == 2 ? code : code.substring(0, code.lastIndexOf("/"));
                String codeName1 = DataServiceUtil.getName(code1, dbId) == null ? "" : DataServiceUtil.getName(code1, dbId);
                name.append(codeName1.substring(codeName1.lastIndexOf("#") + 1));
                if (codeSplit.length >= 3) {
                    String codeName2 = DataServiceUtil.getName(code, dbId) == null ? "" : DataServiceUtil.getName(code, dbId);
                    name.append(Constant.separator_wei).append(codeName2.substring(codeName2.lastIndexOf("#") + 1));
                }
            }

        }
        return name.toString();
    }

    public String getCategoryByName(String coreName, String name) {
        StringBuilder sb = new StringBuilder();
        DataService dataService = (DataService) SpringContextUtil.getBean("dataServiceImpl");
        Config config = (Config) SpringContextUtil.getBean("config");
        if (config.getDialectMap().get(coreName) != null) {
            name = config.getDialectMap().get(coreName) + Constant.separator_wei + name;
        }
        Integer dbId = Integer.valueOf(config.getDbMap().get(coreName));
        String dbName = dataService.Idlibrary().get(dbId).getName();
        sb.append(dbName).append(Constant.separator_wei);
        sb.append(name);
        String coreId = DataServiceUtil.getCode(sb.toString(), dbId) == null ? "" : DataServiceUtil.getCode(sb.toString(), dbId);
        if (!coreId.equals("") && coreId.split(Constant.separator_column).length == 2) {
            coreId = coreId + Constant.separator_column + Constant.separator_asterisk + " OR " + Constant.ColumnId + ":" + coreId;
        }
        return coreId;
    }


    /**
     * 如果存在改配置，则加载配置到 biMaps
     */
    private void getBiMap(String xmlName) {
        Document doc = null;
        try {
            String path = xmlName + ".xml";
            Resource resource = new ClassPathResource(path);
            if (!resource.exists()) {
                return;
            }
            //利用输入流获取XML文件内容
            BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream(), "UTF-8"));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                buffer.append(line);
            }
            br.close();
            doc = DocumentHelper.parseText(buffer.toString());
        } catch (Exception e) {
            logger.error("解析xml文件出错 {} ", xmlName, e);
            return;
        }
        BiMap<String, String> biMap = new BiMap();
        Element root = doc.getRootElement();
        Iterator childNodes = root.elementIterator();
        while (childNodes.hasNext()) {
            Element node = (Element) childNodes.next();
            String key = node.attributeValue("Name");
            String value = node.attributeValue("Value");
            biMap.put(key, value);
            List subNodeList = node.elements();
            if (subNodeList == null || subNodeList.size() == 0) {
                continue;
            }
            Iterator subNodes = subNodeList.iterator();
            while (subNodes.hasNext()) {
                Element subNode = (Element) subNodes.next();
                String subKey = subNode.attributeValue("Name");
                String subValue = value + "#" + subNode.attributeValue("Value");
                biMap.put(subKey, subValue);
            }
        }
        biMaps.put(xmlName, biMap);
    }

}
