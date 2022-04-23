package com.cloud.spring.app;

import com.cloud.common.constant.BaseConstant;
import com.cloud.common.util.EncryptDecodeUtils;
import com.cloud.common.util.JwtUtils;
import com.cloud.common.vo.LoginUserVO;
import com.cloud.spring.exception.CommonException;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;

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



    /**
     * 获取request
     * @return
     */
    public static HttpServletRequest getRequest(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        return attributes.getRequest();
    }

    /**
     * 请求头获取base64加密的用户信息
     * @return
     */
    public static LoginUserVO getBase64User(){
        String text = getRequest().getHeader(BaseConstant.AUTH_TOKEN_USER);
        if (Objects.isNull(text)){
            throw new CommonException("请求不存在用户信息");
        }
        return EncryptDecodeUtils.base64Dec(text, LoginUserVO.class);
    }

    /**
     * 请求头获取Token
     * @return
     */
    public static String getToken(){
        String token = getRequest().getHeader(BaseConstant.AUTH_TOKEN);
        if (Objects.isNull(token)){
            throw new CommonException("Token不存在");
        }
      return token;
    }
}
