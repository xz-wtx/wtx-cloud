package com.cloud.spring.feign;

import com.cloud.common.constant.BaseConstant;
import com.cloud.config.fegin.FeignConfig;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
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
public class ExtFeignConfig extends FeignConfig {


    @Override
    public void extRequestTemplate(RequestTemplate requestTemplate) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        // 老请求
        HttpServletRequest request = requestAttributes.getRequest();
        //token
        String authorization = request.getHeader(BaseConstant.AUTH_TOKEN);
        requestTemplate.header(BaseConstant.AUTH_TOKEN, authorization);
        //用户信息
        String user = request.getHeader(BaseConstant.AUTH_TOKEN_USER);
        requestTemplate.header(BaseConstant.AUTH_TOKEN_USER, user);
    }
}