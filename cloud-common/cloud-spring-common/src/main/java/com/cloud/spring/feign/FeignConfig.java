package com.cloud.spring.feign;


import com.cloud.common.constant.BaseConstant;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


/**
 * feign配置，请求头添加token
 * @author 60003404
 */
@Configuration
@Slf4j
public class FeignConfig {


    @Bean("requestInterceptor")
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            log.info("进入feign拦截器...");
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            assert requestAttributes != null;
            HttpServletRequest request = requestAttributes.getRequest();// 老请求
            String authorization = request.getHeader(BaseConstant.AUTH_TOKEN);
            requestTemplate.header(BaseConstant.AUTH_TOKEN, authorization);
        };
    }
}