package com.cloud.user.exception;

import com.cloud.spring.exception.GlobalExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * @author wtx
 * @Description TODO 统一处理拦截指定controller异常,不拦截feign调用异常否则seata不会回滚
 * @Date 2022/4/19 11:59
 * @Version 1.0
 */

@ControllerAdvice(basePackages = {"com.cloud.user.controller.admin"})
public class CustomGlobalExceptionHandler extends GlobalExceptionHandler {

}
