package com.cloud.spring.dataSource.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.cloud.spring.dataSource.DataSourceContextHolder;
import com.cloud.spring.dataSource.handler.MyMetaObjectHandler;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import java.util.Map;


/**
 * @Description TODO 配置MyBatisPlus 根据不同包读不同数据源
 * @Date 2022/3/23
 * @Author WTX
 * @Version
 **/
@Configuration
@ConfigurationProperties(prefix = "spring")
public  class MyBatisPlusConfig {

    public Map<String, DataSourceProperties> datasource;

    public Map<String, DataSourceProperties> getDatasource() {
        return datasource;
    }
    public void setDatasource(Map<String, DataSourceProperties> datasource) {
        this.datasource = datasource;
    }
    /**
     * 配置事务管理器
     * @return
     */
    @Bean(name = "multipleTransactionManager")
    @Primary
    public DataSourceTransactionManager multipleTransactionManager() {
        return new DataSourceTransactionManager(multipleDataSource());
    }

    /**
     * 动态数据源配置,重写当前方法
     */
    @Bean(name = "multipleDataSource")
    @Primary
    public DataSourceContextHolder multipleDataSource(){
        return null;
    };

    /**
     * 会话工厂
     * @return
     * @throws Exception
     */
    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(multipleDataSource());
        MybatisConfiguration configuration = new MybatisConfiguration();
        MybatisConfig.getMybatisConfiguration(sqlSessionFactory);
        // 添加插件功能
        sqlSessionFactory.setPlugins(mybatisPlusInterceptor());
        //扫描 mapper 路径
        sqlSessionFactory.setConfiguration(configuration);

        //全局配置
        GlobalConfig globalConfig = GlobalConfigUtils.defaults();
        //mybatis-plus全局配置设置元数据对象处理器为自己实现的那个
        globalConfig.setMetaObjectHandler(new MyMetaObjectHandler());
        sqlSessionFactory.setGlobalConfig(globalConfig);

        //读取xml
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        //默认必须要有一个xml文件
        Resource[] resource = resolver.getResources("classpath:mapper/**/**.xml");
        sqlSessionFactory.setMapperLocations(resource);
        return sqlSessionFactory.getObject();
    }




    /**
     * 自从mybatis-plus某版本3.4/3.5更新之后，原先的分页插件paginationInterceptor无法正常使用，这里给出最新的solution。
     *
     * paginationInterceptor 变更为 paginationInnerInterceptor
     *
     * https://blog.csdn.net/moshowgame/article/details/123058673
     * @return
     */
    MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 添加分页插件
        interceptor.addInnerInterceptor(paginationInnerInterceptor());
        return interceptor;
    }


    //分页插件
     PaginationInnerInterceptor paginationInnerInterceptor() {
        PaginationInnerInterceptor paginationInterceptor = new PaginationInnerInterceptor();
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        paginationInterceptor.setMaxLimit(-1L);
        paginationInterceptor.setDbType(DbType.MYSQL);
        // 开启 count 的 join 优化,只针对部分 left join
        paginationInterceptor.setOptimizeJoin(true);
        return paginationInterceptor;
    }

}
