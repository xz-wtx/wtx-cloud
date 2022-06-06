package com.cloud.user;

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
@EnableFeignClients(basePackages = {
       "com.cloud.wechat.feign",
        "com.cloud.order.feign"
    })
@SpringBootApplication
@EnableConfigurationProperties
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);

    }

}
