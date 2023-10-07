package com.student.zhaokangwei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ModuleElasticSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModuleElasticSearchApplication.class, args);
    }

}
