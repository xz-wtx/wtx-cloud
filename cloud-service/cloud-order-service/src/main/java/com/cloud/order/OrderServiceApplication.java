package com.cloud.order;


import com.cloud.spring.imports.ImportCustomSpringConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

/**
 *
 * @author wtx
 */
@Import({ImportCustomSpringConfig.class})
@EnableFeignClients(basePackages = {"com.cloud.goods.feign"})
@SpringBootApplication
@EnableConfigurationProperties
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

}
