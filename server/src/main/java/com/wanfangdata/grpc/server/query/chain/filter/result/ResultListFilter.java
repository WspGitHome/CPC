package com.wanfangdata.grpc.server.query.chain.filter.result;


import com.wanfangdata.api.chain.Filter;
import com.wanfangdata.api.chain.FilterException;
import com.wanfangdata.grpc.server.query.chain.SolrResponse;
import com.wanfangdata.grpc.server.query.chain.util.CategoryData;
import com.wanfangdata.grpc.server.query.chain.util.DateUtil;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author FLY
 * @date 2019-11-1
 */
public class ResultListFilter implements Filter<QueryResponse, SolrResponse> {

    private static final String ID = "Id";
    //高亮后缀
    private static final String HL = "_HL";

    private static final int LENGTH = 300;
    // 定义HTML标签的正则表达式
    private static final String REGEX_HTML = "(?!<(em|/em).*?>)<.*?>";

    private static final String Highlight = "<em style='color:#ef7d24;'>";

    private static final String CATEGORYA = "CategoryA";
    private static final String CATEGORYB = "CategoryB";
    private static final String COLUMNID = "ColumnId";

    private static final Pattern pattern = Pattern.compile(REGEX_HTML, Pattern.CASE_INSENSITIVE);


    @Override
    public void doFilter(QueryResponse queryResponse, SolrResponse solrResponse) throws FilterException {
        SolrDocumentList solrDocumentList = queryResponse.getResults();
        if (solrDocumentList == null || solrDocumentList.size() < 1) {
            return;
        }
        boolean hasHighLight = false;
        Map<String, Map<String, List<String>>> highlightResults = queryResponse.getHighlighting();
        if (highlightResults != null && highlightResults.size() > 1) {
            hasHighLight = true;
        }

        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        for (SolrDocument sd : solrDocumentList) {
            Map<String, String> dic = parserDocument(sd, solrResponse.getCore());
            list.add(dic);
            //设置高亮
            if (hasHighLight) {
                highLight(highlightResults.get(dic.get(ID)), dic);
            }
        }
        solrResponse.setSearchResults(list);
    }


    private Map<String, String> parserDocument(SolrDocument sd, String core) {
        Collection<String> fieldNames = sd.getFieldNames();
        Map<String, String> dic = new HashMap<>();
        for (String field : fieldNames) {
            String fields = field.toString().trim();
            Object obj = sd.getFieldValue(fields);
            if (obj instanceof String) {
                String value = obj.toString();
                if (value.length() > LENGTH) {
                    value = value.substring(0, LENGTH);
                }
                if (fields.equals("Date") && value.contains("org.apache.lucene.document.StoredField")) {
                    value = value.split("Date:")[1];
                    value = value.split(" ")[0];
                }
                //正则替换html标签
                if (fields.equalsIgnoreCase("Content")) {
                    Matcher matcher = pattern.matcher(value);
                    if (matcher.find()) {
                        value = matcher.replaceAll("");
                    }
                    value = value.replace("<em>", "<em style='color:#ef7d24;'>");
                }
                dic.put(fields, value);
            } else if (obj instanceof ArrayList) {
                List values = (ArrayList) obj;
                if (field.equalsIgnoreCase(COLUMNID)) {
                    values = filterCategory(values, core);
                }
                String temp = String.join(",", values);
                if (field.equalsIgnoreCase(COLUMNID)) {
                    dic.put(CATEGORYB, temp);
                } else {
                    dic.put(fields, temp);
                }
            } else if (obj instanceof Date) {
                Date value = (Date) obj;
                String temp = DateUtil.formatDateView(value);
                dic.put(fields, temp);
            }
        }
        return dic;
    }

    private static List<String> filterCategory(List list, String core) {
        List<String> names = new ArrayList<>();
        for (Object value : list) {
            String name = CategoryData.getInstance().getCategoryByCode(core, value.toString());
            if (name == null || name.equals("null")) {
                name = value.toString();
            }
            if (!name.startsWith("无")) {
                names.add(name);
            }
        }
        return names;
    }

    private static void highLight(Map<String, List<String>> highLightMap, Map<String, String> map) {
        if (highLightMap == null && highLightMap.size() == 0) {
            return;
        }
        for (String key : highLightMap.keySet()) {
            List<String> values = highLightMap.get(key);
            if (values == null || values.size() == 0) {
                continue;
            }
            if (values.size() == 1) {
                String value = values.get(0);
                //正则替换html标签
                if (key.equalsIgnoreCase("Content")) {
                    if (value.length() > LENGTH) {
                        value = value.substring(0, LENGTH);
                    }
                    Matcher matcher = pattern.matcher(value);
                    if (matcher.find()) {
                        value = matcher.replaceAll("");
                    }
                }
                map.put(key + HL, value);
                continue;
            }
            String highlight = "";
            for (String value : values) {
                if (highlight == "") {
                    highlight = value;
                } else {
                    highlight += "," + value;
                }
            }
            map.put(key + HL, highlight);
        }
    }

}
