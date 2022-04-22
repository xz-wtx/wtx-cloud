package com.cloud.goods;

import com.cloud.spring.imports.ImportAll;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

/**
 *
 * @author wtx
 */
@Import({ImportAll.class})
@SpringBootApplication
@EnableConfigurationProperties
public class GoodsServiceApplication {

    public static void main(String[] args) {
        System.setProperty("project.name","goods服务");
        SpringApplication.run(GoodsServiceApplication.class, args);
    }

}
