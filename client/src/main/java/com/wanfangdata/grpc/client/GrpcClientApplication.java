package com.wanfangdata.grpc.client;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableSwagger2Doc
@SpringBootApplication
public class GrpcClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(GrpcClientApplication.class, args);
	}

}
