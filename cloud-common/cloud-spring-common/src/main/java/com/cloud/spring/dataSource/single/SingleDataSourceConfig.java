package com.cloud.spring.dataSource.single;

import com.cloud.spring.dataSource.DataSourceContextHolder;
import com.cloud.spring.dataSource.config.MyBatisPlusConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author wtx
 * @Description
 * TODO 初始化数据源,默认从数据源集合中读取 spring.application.name的数据源,或通过指定setDataSourceName指定数据源，
 * TODO 也可通过extTargetDataSources扩展数据源
 * @Date 2022/4/22 14:45
 * @Version 1.0
 */
@Log4j2
public  class SingleDataSourceConfig extends MyBatisPlusConfig {

    @Value("${spring.application.name}")
    public String name;


    public List<String> dataSourceNameList;
    
    
    public void setDataSourceName(List<String> lists){
       this.dataSourceNameList=lists;
    }
    

    @Override
    public DataSourceContextHolder multipleDataSource() {
        DataSourceContextHolder b = new DataSourceContextHolder();
        HashMap<Object, Object> hashMap = new HashMap<>(1);

        if (CollectionUtils.isEmpty(dataSourceNameList)){

            if (name!=null){
                dataSourceNameList=new ArrayList<>(1);
                dataSourceNameList.add(name);
            }
        }

        if (CollectionUtils.isEmpty(dataSourceNameList)){
            throw new RuntimeException("未查到《" + name + "》对应的数据源,请先配置数据源");
        }

        for (String datasourceName : dataSourceNameList) {
            final DataSourceProperties value = datasource.get(datasourceName);
            if (Objects.isNull(value)) {
                throw new RuntimeException("未查到《" + datasourceName + "》对应的数据源,请先配置数据源");
            }
            final DataSource dataSource = value.initializeDataSourceBuilder().build();
            hashMap.put(datasourceName, dataSource);
        }
        extTargetDataSources(hashMap);
        b.setTargetDataSources(hashMap);
        final Object o = hashMap.values().stream().findFirst().get();
        b.setDefaultTargetDataSource(o);
        log.info("-----------加载数据源配置完成-----------");
        return b;
    }


    /**
     * 扩展数据源
     * @param hashMap key数据源名称 ：value数据源DataSource
     */
    public void extTargetDataSources(HashMap<Object, Object> hashMap) {

     };
}

