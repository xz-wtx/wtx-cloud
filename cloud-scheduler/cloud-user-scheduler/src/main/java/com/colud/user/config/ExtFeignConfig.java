package com.colud.user.config;

import com.cloud.common.constant.BaseConstant;
import com.cloud.common.util.EncryptDecodeUtils;
import com.cloud.common.vo.LoginUserVO;
import com.cloud.config.fegin.FeignConfig;
import feign.RequestTemplate;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;

/**
 * @author 60003404
 * @Description TODO
 * @Date 2022/4/22 22:54
 * @Version 1.0
 */
@Log4j2
@Configuration
public class ExtFeignConfig extends FeignConfig {
    @Override
    public void extRequestTemplate(RequestTemplate requestTemplate) {
        requestTemplate.header(BaseConstant.AUTH_TOKEN_USER, EncryptDecodeUtils.base64Enc(new LoginUserVO(){{
            setUserName("user调度器");
        }}));
    }
}
