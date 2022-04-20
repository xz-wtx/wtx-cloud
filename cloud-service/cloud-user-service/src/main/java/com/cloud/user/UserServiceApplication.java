package com.cloud.user;


import com.cloud.spring.imports.ImportAll;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

/**
 *
 * @author wtx
 */
@Import({ImportAll.class})
@EnableFeignClients(basePackages = {"com.cloud.user.feign","com.cloud.wechat.feign"})
@SpringBootApplication
@EnableConfigurationProperties
public class UserServiceApplication {

    public static void main(String[] args) {
        System.setProperty("project.name","user服务");
        SpringApplication.run(UserServiceApplication.class, args);
    }

}
