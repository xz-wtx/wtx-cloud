package com.cloud.user;


import com.cloud.common.util.JwtUtils;
import com.cloud.common.vo.LoginUserVO;
import com.cloud.spring.imports.ImportAll;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

/**
 *
 * @author wtx
 */
@Import({ImportAll.class})
@EnableFeignClients(basePackages = {
       "com.cloud.wechat.feign",
        "com.cloud.order.feign"
    })
@SpringBootApplication
@EnableConfigurationProperties
public class UserServiceApplication {

    public static void main(String[] args) {
        System.out.println(JwtUtils.createJWT(new LoginUserVO(){{setUserName("213");setId(1);}}));
        //SpringApplication.run(UserServiceApplication.class, args);
    }

}
