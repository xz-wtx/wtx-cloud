package com.colud.user;

import com.cloud.config.job.XxlJobConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@Import(XxlJobConfig.class)
@EnableFeignClients(basePackages = {"com.cloud.user.feign"})
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class UserSchedulerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserSchedulerApplication.class, args);
    }

}
