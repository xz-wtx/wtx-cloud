package com.cloud.spring.dataSource.config;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.type.JdbcType;
import org.springframework.beans.factory.annotation.Value;

/**
 *  mybatis部分配置
 * @author wtx
 */

public class MybatisConfig {

    public static String active;

    @Value("${spring.profiles.active:}")
    public void setActive(String active) {
        MybatisConfig.active = active;
    }

    public static void getMybatisConfiguration(MybatisSqlSessionFactoryBean sessionFactory) {
        // 导入mybatis配置
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        //开启驼峰
        configuration.setMapUnderscoreToCamelCase(true);
        //关闭缓存
        configuration.setCacheEnabled(false);
        // 配置打印sql语句
        if (!"prd".equals(active)) {
            //开启sql日志
            configuration.setLogImpl(StdOutImpl.class);
        }
        sessionFactory.setConfiguration(configuration);
    }
}
