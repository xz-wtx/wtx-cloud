package com.cloud.common.vo;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

/**
 * @author wtx
 * @Description TODO 用户登录后的一些必要信息信息
 * @Date 2022/4/14 20:38
 * @Version 1.0
 */
@Log4j2
@Data
public class LoginUserVO {

    public Integer id;

    public String userName;

    public String token;

    public String signStr;

}
