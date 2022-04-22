package com.cloud.spring.sysProperty;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author 60003404
 * @Description TODO 系统参数
 * @Date 2022/4/22 16:31
 * @Version 1.0
 */
@Component
public class SystemPropertyConfig {


    @Value("${spring.application.name:}")
    public String name;



    @PostConstruct
    public void init(){
        System.out.println("-----------------------------"+name);
        System.setProperty("project.name",name+"服务");
    }
}
