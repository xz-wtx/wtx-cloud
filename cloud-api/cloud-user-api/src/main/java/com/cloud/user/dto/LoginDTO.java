package com.cloud.user.dto;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

/**
 * @author wtx
 * @Description TODO
 * @Date 2022/4/14 19:05
 * @Version 1.0
 */
@Data
@Log4j2
public class LoginDTO {

    public String password;

    public String account;

    public String openId;
}
