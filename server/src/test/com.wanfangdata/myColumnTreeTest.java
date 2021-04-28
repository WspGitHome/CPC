package com.wanfangdata;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.wanfangdata.grpc.server.GrpcServerApplication;
import com.wanfangdata.grpc.server.db.dbentity.Column;
import com.wanfangdata.grpc.server.db.dbservice.DataService;
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
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
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
@SpringBootTest(classes = GrpcServerApplication.class)
@ActiveProfiles("dev")
public class myColumnTreeTest {

    @Autowired
    DataService dataServiceImpl;

    @Test
    public void testColum() {
        dataServiceImpl.columnTreeByLibraryId(2);
        System.out.println(1);


    }
}
