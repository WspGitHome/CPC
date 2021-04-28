package com.wanfangdata.grpc.server.config;


import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class SolrConfig {
        @Autowired
        private Environment environment;

        @Bean
        public SolrClient solrClient() {
            HttpSolrClient client = new HttpSolrClient.Builder(environment.getRequiredProperty("spring.data.solr.host"))
                    .withConnectionTimeout(10000)
                    .withSocketTimeout(60000)
                    .build();
            return client;
        }

}