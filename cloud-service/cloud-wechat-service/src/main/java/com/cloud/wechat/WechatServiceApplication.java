package com.cloud.wechat;

import com.cloud.spring.imports.ImportAll;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

/**
 * @author wtx
 */
@Import({ImportAll.class})
@EnableFeignClients(basePackages = {"com.cloud.wechat.feign", "com.cloud.user.feign"})
@SpringBootApplication
@EnableConfigurationProperties
public class WechatServiceApplication {

    public static void main(String[] args) {
        System.setProperty("project.name","wechat服务");
        SpringApplication.run(WechatServiceApplication.class, args);
    }

}
