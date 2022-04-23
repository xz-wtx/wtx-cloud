package com.cloud.config.fegin;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;


/**
 * feign配置，请求头添加token
 * @author 60003404
 */
@Slf4j
public abstract class FeignConfig {

    @Bean("requestInterceptor")
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            log.info("进入feign拦截器...");
           extRequestTemplate(requestTemplate);
        };
    }

    /**
     * 拦截openFeign进行扩展
     * @param requestTemplate
     */
    public abstract void extRequestTemplate(RequestTemplate requestTemplate);
}