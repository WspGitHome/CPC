package com.wanfangdata.grpc.server.service;

import com.wanfangdata.grpc.CatagoryRequest;
import com.wanfangdata.grpc.CatagoryResponse;
import com.wanfangdata.grpc.CategoryServiceGrpc;
import com.wanfangdata.grpc.server.query.chain.dao.SolrDao;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


@GrpcService
public class CatagoryService extends CategoryServiceGrpc.CategoryServiceImplBase {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SolrDao solrDao;

    @Override
    public void getCategory(CatagoryRequest request, StreamObserver<CatagoryResponse> responseObserver) {
        String bookId=request.getBookId();

    }
}