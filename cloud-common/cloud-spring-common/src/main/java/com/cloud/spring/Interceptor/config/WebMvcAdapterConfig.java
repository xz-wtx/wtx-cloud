package com.cloud.spring.Interceptor.config;
import com.cloud.spring.Interceptor.AuthenticationInterceptor;
import com.cloud.spring.Interceptor.LoginUserHandlerMethodArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


/**
 *  加载配置文件
 * @author wtx
 */
@Configuration
public class WebMvcAdapterConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration ir = registry.addInterceptor(new AuthenticationInterceptor());
        ir.addPathPatterns("/**");
    }

    /**
     * 添加参数解析器
     * @param resolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginUserHandlerMethodArgumentResolver());
    }

    /**
     * 封装登录参数 在控制层使用示例
     * public void test(@LoginUser LoginUserVO loginUser){
     *
     * }
     * @return
     */
    @Bean
    public LoginUserHandlerMethodArgumentResolver loginUserHandlerMethodArgumentResolver(){
        return new LoginUserHandlerMethodArgumentResolver();
    }

}
