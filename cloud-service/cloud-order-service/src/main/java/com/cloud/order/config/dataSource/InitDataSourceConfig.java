package com.cloud.order.config.dataSource;


import com.cloud.spring.dataSource.single.SingleDataSourceConfig;
import lombok.extern.log4j.Log4j2;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;




/**
 * @author wtx
 * @Description
 * TODO 初始化数据源,默认从数据源集合中读取 spring.application.name的数据源
 * TODO 多数据源请参考 UserServiceApplication
 * @Date 2022/4/15 18:21
 * @Version 1.0
 */

@Log4j2
@Configuration
@MapperScan(value = {"com.cloud.order.mapper"})
public class InitDataSourceConfig extends SingleDataSourceConfig {

}
