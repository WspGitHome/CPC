package com.wanfangdata.grpc.client.service;

import com.wanfangdata.grpc.SearchRequest;
import com.wanfangdata.grpc.SearchResponse;
import com.wanfangdata.grpc.SearchServiceGrpc;
import com.wanfangdata.grpc.Searcher;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import io.grpc.netty.NegotiationType;
import io.grpc.netty.NettyChannelBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: xcbeyond
 * @Date: 2019/3/7 09:10
 */
@Service
public class GrpcClientService {

    @Autowired
    Channel channel;

    public String sendMessage(String name) {
        try {
            SearchServiceGrpc.SearchServiceBlockingStub  searchBlockingStub = SearchServiceGrpc.newBlockingStub(channel);
            SearchResponse response = searchBlockingStub.getSearch(SearchRequest.newBuilder().setSearcher(Searcher.newBuilder().setQuery("志").setCore("FZLocalChronicle").addHighLight("BookTitle").build()).build());
            return response.getResulter().getResults();
        } catch (final StatusRuntimeException e) {
            return "FAILED with " + e.getStatus().getCode();
        }
    }

    public static void main(String[] args){
        ManagedChannel managedChannel = NettyChannelBuilder.forAddress("10.20.21.122", 9903).negotiationType(NegotiationType.PLAINTEXT).build();
        SearchServiceGrpc.SearchServiceBlockingStub  searchBlockingStub1 = SearchServiceGrpc.newBlockingStub(managedChannel);
        SearchResponse response = searchBlockingStub1.getSearch(SearchRequest.newBuilder().setSearcher(Searcher.newBuilder().setQuery("aa").build()).build());
        System.out.print(response.getResulter().getResults());
    }
}
