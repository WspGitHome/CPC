package com.wanfangdata.grpc.server.mainten;

import com.wanfangdata.api.chain.MockFilterChain;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 *
 *@author FLY
 *@date 2019年7月19日
 *
 */
@Data
public class MaintenFilterChain {

    private static final String defaultFilterChain="default";

   Map<String,MockFilterChain> mockFilterChains;

   public MockFilterChain getMockFilterChain(String searchType){
       if(null==searchType){
           return mockFilterChains.get(defaultFilterChain);
       }
       MockFilterChain mockFilterChain=mockFilterChains.get(searchType);
       if(mockFilterChain==null){
           return mockFilterChains.get(defaultFilterChain);
       }
       return mockFilterChain;
   }
}