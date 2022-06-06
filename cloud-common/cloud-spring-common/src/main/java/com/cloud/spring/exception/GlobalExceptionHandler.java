package com.cloud.spring.exception;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.cloud.common.enums.CommonEnum;
import com.cloud.common.exception.CommonException;
import com.cloud.common.response.BR;
import com.cloud.common.response.R;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * @Description 全局异常
 * @Date 2020/6/28
 * @Author peaks
 * @Version
 **/
@Log4j2
public class GlobalExceptionHandler {

    public static String active;
    public  static String name;

    @Value("${spring.profiles.active}")
    public  void setActive(String active) {
        GlobalExceptionHandler.active = active;
    }
    @Value("${spring.application.name}")
    public  void setName(String name) {
        GlobalExceptionHandler.name = name;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public <T> R<T> runtimeExceptionHandler(NativeWebRequest request, Exception e) {
        e.printStackTrace();
        if (e instanceof CommonException ce) {
            return BR.genErrorResult(ce.getCode(),ce.getErrorMsg());
        }
        if (e instanceof TokenExpiredException ce){
            return BR.genErrorResult(ce.getMessage());
        }
        return BR.genResult(CommonEnum.ERROR_500);
    }

}
