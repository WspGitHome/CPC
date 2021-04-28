package com.wanfangdata.grpc.client.controller;

import com.wanfangdata.grpc.client.service.GrpcClientService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
public class GrpcClientController {
    @Resource
    private GrpcClientService grpcClientService;

    @RequestMapping("/")
    public String printMessage(@RequestParam(defaultValue = "jack") String name) {
        return grpcClientService.sendMessage(name);
    }
}
