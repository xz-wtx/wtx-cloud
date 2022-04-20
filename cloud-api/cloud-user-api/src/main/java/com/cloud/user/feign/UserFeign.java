package com.cloud.user.feign;

import com.cloud.user.entity.SysUserEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * @author wtx
 * @Description TODO
 * @Date 2022/4/6 14:19
 * @Version 1.0
 */

@FeignClient("user")
public interface UserFeign {

    /**
     * 新增用户
     * @return
     */
    @GetMapping(value = "/feign/user/insert")
     String insertUser(@RequestBody SysUserEntity sysUser);
}
