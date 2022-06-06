package com.cloud.gateway.config;

import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.cloud.gateway.handler.MyGatewayBlockExceptionHandler;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * @author wtx
 */
@Configuration
public class GatewayConfiguration {



    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public MyGatewayBlockExceptionHandler gatewayBlockExceptionHandler() {

        // Register the block exception handler for Spring Cloud Gateway.
        return new MyGatewayBlockExceptionHandler();
    }

    @Bean
    @Order(-1)
    public GlobalFilter mySentinelGatewayFilter() {
        return new SentinelGatewayFilter();
    }

}