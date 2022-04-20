package com.cloud.spring.Interceptor;


import com.cloud.spring.Interceptor.AuthenticationInterceptor;
import com.cloud.common.annotation.LoginUser;
import com.cloud.common.vo.LoginUserVO;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


/**
 * 封装登录信息
 * @author wtx
 */
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(LoginUserVO.class)
                && parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        return  webRequest.getAttribute(AuthenticationInterceptor.CURRENT_USER, RequestAttributes.SCOPE_REQUEST);
    }
}
