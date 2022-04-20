package com.cloud.spring.register;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * @author wtx
 * @Description TODO 加载ControllerApiRequestMappingHandlerMapping配置
 * @Date 2022/3/1 9:12
 * @Version 1.0
 */
@Log4j2
@Configuration
//这个注解很重要啊，不写不生效
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WebMvcRegisterConfig implements WebMvcRegistrations {

    @Value("${show.mapping.path:true}")
    private boolean showMappingPath;


    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new ControllerApiRequestMappingHandlerMapping(showMappingPath);
    }

}
