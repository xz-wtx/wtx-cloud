package com.cloud.user.controller.admin;

import com.cloud.common.response.BR;
import com.cloud.user.dto.LoginDTO;
import com.cloud.user.entity.SysUserEntity;
import com.cloud.user.service.SysUserService;
import com.cloud.common.response.R;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author wtx
 * @since 2022-04-11
 */
@Log4j2
@RestController
@RequestMapping("/user")
public class SysUserController extends BaseController{

    @Autowired
    SysUserService sysUserService;

    /**
     * 用户登录
     * @param loginDTO
     * @return
     */
    @RequestMapping("login")
    public R<String> login(@RequestBody LoginDTO loginDTO){
        if (StringUtils.isBlank(loginDTO.getOpenId())){
            if (StringUtils.isAnyBlank(loginDTO.getAccount(),loginDTO.getPassword())){
                return BR.genErrorResult("必填参数不能为空");
            }
        }
        return sysUserService.login(loginDTO);
    }
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
