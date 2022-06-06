package com.cloud.gateway;

import com.cloud.gateway.config.GatewayConfiguration;
import com.cloud.gateway.handler.MyGatewayBlockExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * @author wtx
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class GatewayServiceApplication {

    public static void main(String[] args) {
        //sentinel开启网关模式
        System.setProperty("csp.sentinel.app.type", "1");

        SpringApplication.run(GatewayServiceApplication.class, args);
    }


}
