package com.cloud.spring.imports;

import com.cloud.spring.app.AppContextUtil;
import com.cloud.spring.Interceptor.config.WebMvcAdapterConfig;
import com.cloud.spring.feign.ExtFeignConfig;
import com.cloud.spring.register.WebMvcRegisterConfig;
import com.cloud.spring.sysProperty.SystemPropertyConfig;
import org.springframework.context.annotation.Import;

/**
 * @author wtx
 * @Description TODO
 * @Date 2022/4/16 16:02
 * @Version 1.0
 */

@Import({
        AppContextUtil.class,
//        WebMvcAdapterConfig.class,
        WebMvcRegisterConfig.class,
        ExtFeignConfig.class,
        SystemPropertyConfig.class
    })
public class ImportCustomSpringConfig {

}
