package com.cloud.wechat.config.dataSource;

import com.cloud.spring.dataSource.DataSourceContextHolder;
import com.cloud.spring.dataSource.config.MyBatisPlusConfig;
import lombok.extern.log4j.Log4j2;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Objects;


/**
 * @author wtx
 * @Description TODO 初始化数据源
 * @Date 2022/4/15 18:21
 * @Version 1.0
 */

@Log4j2
@Configuration
@MapperScan(value = {"com.cloud.wechat.mapper"})
public class InitDataSource extends MyBatisPlusConfig {

    @Override
    public DataSourceContextHolder multipleDataSource(){
        DataSourceContextHolder b=new DataSourceContextHolder();
        HashMap<Object, Object> hashMap = new HashMap<>(DataSourceEnums.values().length);
        for (DataSourceEnums enums : DataSourceEnums.values()) {
            if (enums.getFlag()==0){
                final DataSourceProperties value = datasource.get(enums.getValue());
                if (Objects.isNull(value)){
                    throw new RuntimeException("未查到《"+enums.getValue()+"》对应的数据源,请先配置数据源");
                }
                final DataSource dataSource = value.initializeDataSourceBuilder().build();
                hashMap.put(enums.getValue(),dataSource);
            }

        }
        b.setTargetDataSources(hashMap);
        final String o = (String)hashMap.keySet().stream().findFirst().get();
        b.setDefaultTargetDataSource(hashMap.get(o));
        log.info("-----------加载数据源配置完成-----------");
        return b;
    }




}
