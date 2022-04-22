package com.cloud.spring.app;

import com.cloud.common.constant.BaseConstant;
import com.cloud.common.util.JwtUtils;
import com.cloud.common.vo.LoginUserVO;
import com.cloud.spring.exception.CommonException;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author 60003404
 * @Description TODO Servlet工具类
 * @Date 2022/4/20 17:14
 * @Version 1.0
 */
@Log4j2
public class ServletUtils {


    //获取request
    public static HttpServletRequest getRequest(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        return attributes.getRequest();
    }

    //获取Token
    public static String getToken(){
        String token = getRequest().getHeader(BaseConstant.AUTH_TOKEN);
        if (Objects.isNull(token)){
            throw new CommonException("Token不存在");
        }
      return token;
    }

    //获取用户信息
    public static LoginUserVO getUser(){
        String token = getToken();
        final LoginUserVO userVO = JwtUtils.parseJWT(token, LoginUserVO.class);
        if (Objects.isNull(userVO)){
            throw new CommonException("用户信息不存在");
        }
        return userVO;
    }
}
