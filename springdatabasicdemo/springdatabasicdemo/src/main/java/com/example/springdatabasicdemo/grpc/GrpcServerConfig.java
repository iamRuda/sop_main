package com.example.springdatabasicdemo.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class GrpcServerConfig {

    @Autowired
    private InspectedPersonServiceImpl inspectedPersonService;

    @Bean
    public Server grpcServer() throws IOException {
        return ServerBuilder
                .forPort(9090)
                .addService(inspectedPersonService)
                .build()
                .start();
    }
}
