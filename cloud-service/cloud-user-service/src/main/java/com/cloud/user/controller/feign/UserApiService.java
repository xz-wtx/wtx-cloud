package com.cloud.user.controller.feign;

import com.cloud.user.entity.SysUserEntity;
import com.cloud.user.service.SysUserService;
import com.cloud.common.response.R;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Date 2022/4/18 10:22
 * @Version 1.0
 */
@Log4j2
@RestController
@RequestMapping("/user")
public class UserApiService extends BaseFeignService {

    @Autowired
    SysUserService sysUserService;
    /**
     * 新增用户
     * @param sysUser
     * @param <T>
     * @return
     */
    @RequestMapping("insert")
    public <T> R<T> insert(@RequestBody SysUserEntity sysUser)  {
        log.info("openFeign调用成功");
        return sysUserService.insert(sysUser);
    }
}
