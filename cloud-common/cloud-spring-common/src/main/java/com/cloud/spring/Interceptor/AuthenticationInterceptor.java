package com.cloud.spring.Interceptor;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.cloud.common.constant.BaseConstant;
import com.cloud.common.vo.LoginUserVO;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @description: TODO 拦截api请求
 * @author: WTX
 * @create: 2021-01-18 14:59
 **/

@Log4j2
public class AuthenticationInterceptor implements HandlerInterceptor {

    //当前用户标识
    public static String CURRENT_USER="current_user";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        String token = request.getHeader(BaseConstant.AUTH_TOKEN);
        log.info("Token:{},url:{}", token, request.getRequestURL());
        if (handler instanceof HandlerMethod) {
            if (StringUtils.isBlank(token)) {
                return true;
            } else {
                try {
                    //LoginUserVO fromToken = JwtUtils.parseJWT(token, LoginUserVO.class);
                    LoginUserVO fromToken=new LoginUserVO();
                    request.setAttribute(CURRENT_USER, fromToken);
                } catch (Exception e) {
                    e.printStackTrace();
                    if (e instanceof TokenExpiredException){
                        responseBody(response, 401, e.getMessage());
                        return false;
                    }
                    responseBody(response, 500,e.getMessage());
                    return false;
                }
            }
        }

        return true;

    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }


    public void responseBody(HttpServletResponse response, Integer status, String message) {
        response.setCharacterEncoding("UTF-8");
        response.setStatus(status);
        try {
            response.getWriter().write(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}