package com.cloud.goods;

import com.cloud.spring.imports.ImportCustomSpringConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

/**
 *
 * @author wtx
 */
@Import({ImportCustomSpringConfig.class})
@SpringBootApplication
@EnableConfigurationProperties
public class GoodsServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(GoodsServiceApplication.class, args);
    }

}
