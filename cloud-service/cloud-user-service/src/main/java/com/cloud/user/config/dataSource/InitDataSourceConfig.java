package com.cloud.user.config.dataSource;

import com.cloud.spring.dataSource.single.SingleDataSourceConfig;
import lombok.extern.log4j.Log4j2;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author wtx
 * @Description
 * TODO 初始化数据源,默认从数据源集合中读取 spring.application.name的数据源
 * TODO 如果多个数据源，可以按照包名加载不同数据源
 * @Date 2022/4/15 18:21
 * @Version 1.0
 */

@Log4j2
@Configuration
@MapperScan(value = {"com.cloud.user.mapper"})
public class InitDataSourceConfig extends SingleDataSourceConfig {



    InitDataSourceConfig(){
        //指定数据源,可以从枚举中读取flag为0的数据源
        final List<String> list = Arrays.stream(DataSourceEnums.values()).filter(p->p.getFlag()==0).map(DataSourceEnums::getValue).collect(Collectors.toList());
        this.setDataSourceName(list);
    }


    // 还可以手动扩展flag为1的数据源
//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource.user")
//    public DataSource userDataSource() {
//        return DataSourceBuilder.create().build();
//    }
//    /**
//     * 扩展数据源
//     * @param hashMap key数据源名称 ：value数据源DataSource
//     */
//    @Override
//    public void extTargetDataSources(HashMap<Object, Object> hashMap) {
//        hashMap.put("userDataSourceName",userDataSource());
//    };

}
