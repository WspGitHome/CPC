package com.wanfangdata.grpc.client.config;


import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.netty.NegotiationType;
import io.grpc.netty.NettyChannelBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class GrpcConfiguration {
        @Autowired
        private Environment environment;

        @Bean
        public Channel channel(@Value("${grpc.client.server.host}") String host,@Value("${grpc.client.server.port}") Integer port) {
            ManagedChannel channel = NettyChannelBuilder.forAddress(host, port).negotiationType(NegotiationType.PLAINTEXT).build();
            return channel;
        }

}