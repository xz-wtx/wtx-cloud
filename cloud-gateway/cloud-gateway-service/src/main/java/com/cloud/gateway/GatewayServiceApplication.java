package com.cloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wtx
 */
@SpringBootApplication
public class GatewayServiceApplication {

    public static void main(String[] args) {
        //sentinel开启网关模式
        System.setProperty("csp.sentinel.app.type", "1");

        SpringApplication.run(GatewayServiceApplication.class, args);
    }

}
