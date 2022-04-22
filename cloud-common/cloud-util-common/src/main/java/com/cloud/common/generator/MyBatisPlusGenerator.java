package com.cloud.common.generator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;

/**
 * @author wtx
 * @Description TODO
 * @Date 2022/4/14 18:38
 * @Version 1.0
 */
@Log4j2
public class MyBatisPlusGenerator {

    public static Boolean openModule=false;//是否生成分模块
    public static String url="jdbc:mysql://localhost:3306/cloud-goods?useSSL=false&useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&allowMultiQueries=true";
    public static String username="root";
    public static String password="root";

    public static void main(String[] args) {
        String projectPath = System.getProperty("user.dir")+"\\cloud-service\\cloud-goods-service\\";
        createEntity(projectPath);
    }


    //生成实体
    public static void createEntity(String projectPath) {
        if (StringUtils.isBlank(projectPath)){
            throw new RuntimeException("请传入需要生成实体的项目路径");
        }

         String modulePackage="";
         String moduleName="";
        if (openModule){
            modulePackage="module.admin.";
            moduleName="Admin";
        }

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig.Builder(url,username,password).build();

        // 代码生成器
        AutoGenerator mpg =new AutoGenerator(dsc);

        // 全局配置
        GlobalConfig gc = new GlobalConfig.Builder()
                .author("wtx")
                //设置注释的日期格式
                .commentDate("yyyy-MM-dd")
                //指定输出目录,注意使用反斜杠\
                .outputDir(projectPath+ "\\src\\main\\java")
                //使用java8新的时间类型
                .dateType(DateType.TIME_PACK)
                .build();

        // 包配置
        PackageConfig pc = new PackageConfig.Builder()
                //模块名
                .moduleName("")
                //设置父包名
                .parent("com.cloud.goods")
                //设置MVC下各个模块的包名
                .entity("entity")
                .mapper("mapper")
                .service(modulePackage+"service")
                .serviceImpl(modulePackage+"service.impl")
                .controller(modulePackage+"controller")
                .other("other")
                //设置XML资源文件的目录
                .pathInfo(Collections.singletonMap(OutputFile.xml, projectPath+"src\\main\\resources\\mapper"))
                .build();


        // 策略配置
        StrategyConfig strategy = new StrategyConfig
                .Builder()
                //表名 逗号分割
                .addInclude("goods_info")
                .entityBuilder()
                //实体驼峰转换
                .naming(NamingStrategy.underline_to_camel)
                .columnNaming(NamingStrategy.underline_to_camel)
                .enableLombok()
                //Controller策略配置,开启生成@RestController控制器
                .controllerBuilder()
                .enableRestStyle()
                .build();

        //entity 配置：如user ->UserEntity.java
        strategy.entityBuilder().formatFileName("%sEntity");
        //service 配置 如：User-> UserService.java,UserServiceImpl.java
        strategy.serviceBuilder().formatServiceFileName(moduleName+"%sService").formatServiceImplFileName(moduleName+"%sServiceImpl");
        //mapper配置 如：User -> UserMapper.java,UserMapper.xml
        strategy.mapperBuilder().formatMapperFileName("%sMapper").formatXmlFileName("%sMapper");
        //controller配置 如：User -> UserController.java
        strategy.controllerBuilder().formatFileName(moduleName+"%sController");


        mpg.global(gc);
        mpg.packageInfo(pc);
        mpg.strategy(strategy);

        mpg.execute();
    }
}
