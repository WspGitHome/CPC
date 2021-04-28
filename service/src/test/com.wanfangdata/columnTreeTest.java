package com.wanfangdata;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.wanfangdata.cpc.SearchApplication;
import com.wanfangdata.cpc.entity.Column;
import com.wanfangdata.cpc.service.DataService;
import io.swagger.models.auth.In;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Packagename com.wanfangdata
 * @Classname columnTreeTest
 * @Description
 * @Authors Mr.Wu
 * @Date 2020/09/01 15:55
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SearchApplication.class)
//@TestPropertySource("classpath:application-dev.properties")
@ActiveProfiles("dev")
public class columnTreeTest {

    @Autowired
    DataService dataServiceImpl;
    @Autowired
    SolrClient solrClient;

    @Test
    public void testColum() {
        Integer libId = 2;
        BiMap<String, String> stringStringBiMap = dataServiceImpl.columnTreeByLibraryId(libId);
        Column column = dataServiceImpl.columnTree(libId);
        String query = "LibraryId:" + libId + " AND Title:（一）台州中学退休教师协会简介";
        SolrQuery request = new SolrQuery();
        request.setFacet(true);
        request.setRows(0);
        request.setParam("facet.field", "ColumnId");
        request.setQuery(query);
        request.setFacetLimit(500);
        QueryResponse response = null;
        try {
            response = solrClient.query("FZCPCLocalChronicleItem", request);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BiMap<String, Long> facetResultMap = HashBiMap.create();
        List<FacetEntity> facetResult = new ArrayList<>();
        List<FacetField> facetFields = response.getFacetFields();

        facetFields.get(0).getValues().stream().filter(x -> x.getName().startsWith(libId + "/")).forEach(x -> {
            FacetEntity facetEntity = new FacetEntity();
            facetEntity.setCount(x.getCount());
            facetEntity.setValue(x.getName());
            facetEntity.setField(stringStringBiMap.get(x.getName()));
            facetResult.add(facetEntity);

        });
        deleteColumTree(column, facetResult.stream().map(e -> e.getField()).collect(Collectors.toList()));
        FacetEntity entity = new FacetEntity();
        toEntity(column, entity, facetResult);
        System.out.println(1);
    }

    private void deleteColumTree(Column column, List<String> collect) {
        if (column.getChildren() != null) {
            for (Column e : column.getChildren()) {
                if (e != null && !forContain(collect, e.getName())) {
                    column.getChildren().remove(e);
                    continue;
                }
                if (e != null && column.getChildren() != null) {
                    deleteColumTree(e, collect);
                }
            }

        }
    }


    private FacetEntity toEntity(Column column, FacetEntity facetEntity, List<FacetEntity> facetFields) {
        facetEntity.setField(column.getName());
        facetEntity.setCount(0L);
        facetFields.stream().forEach(facetField -> {
            if (column.getName() != null && facetField.getField().contains(column.getName()))
                facetEntity.setCount(facetEntity.getCount() + facetField.getCount());
        });
        if (column.getChildren() != null) {
            List<FacetEntity> facetEntityList = new ArrayList<>();
            for (Column x : column.getChildren()) {
                FacetEntity facetEntites = new FacetEntity();
                facetEntityList.add(toEntity(x, facetEntites, facetFields));
            }
            facetEntity.setSubdata(facetEntityList);
        }
        return facetEntity;
    }

    private boolean forContain(List<String> collect, String name) {
        boolean isContains = false;
        for (String vaule : collect) {
            if (vaule.contains(name)) {
                System.out.println("--------->" + name);
                isContains = true;
                break;
            }
        }
        return isContains;
    }
}
