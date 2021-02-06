package com.wordmemoriser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EntityScan
public class SpringWordMemoriserApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringWordMemoriserApplication.class, args);
    }
}
