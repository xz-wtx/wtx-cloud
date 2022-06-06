package com.cloud.user.controller.admin;

import com.cloud.common.response.BR;
import com.cloud.user.dto.LoginDTO;
import com.cloud.user.dto.SysUserDTO;
import com.cloud.user.entity.SysUserEntity;
import com.cloud.user.service.SysUserService;
import com.cloud.common.response.R;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

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
    @Autowired
    StringRedisTemplate redisTemplate;

    /**
     * 用户登录
     * @param loginDTO
     * @return
     */
    @PostMapping("login")
    public R<String> login(@RequestBody LoginDTO loginDTO){
        if (StringUtils.isBlank(loginDTO.getOpenId())){
            if (StringUtils.isAnyBlank(loginDTO.getAccount(),loginDTO.getPassword())){
                return BR.genErrorResult("必填参数不能为空");
            }
        }
        final String code = redisTemplate.opsForValue().get("image_code:" + loginDTO.getCaptchaKey());
        if (StringUtils.isBlank(code)){
            return BR.genErrorResult("验证码过期");
        }
        if (!code.equals(loginDTO.getCaptchaCode())){
            return BR.genErrorResult("验证码错误");
        }

        return sysUserService.login(loginDTO);
    }

    /**
     * 通过token获取菜单
     * @return
     */
    @PostMapping("getUserMenu")
    public R<String> getUserMenu(){
        return sysUserService.getUserMenu();
    }
    /**
     * 分页查询用户信息
     * @param sysUser
     * @param <T>
     * @return
     */
    @GetMapping("getPageUserList")
    public <T> R<T> getPageUserList(SysUserDTO sysUser){

        return sysUserService.getPageUserList(sysUser);
    }

    /**
     * 查询单个用户信息
     * @param <T>
     * @return
     */
    @GetMapping("/{id}")
    public <T> R<T> getUser(@PathVariable("id") Integer id){

        return sysUserService.getUser(id);
    }

    /**
     * 新增用户
     * @param sysUser
     * @param <T>
     * @return
     */
    @PostMapping("")
    public <T> R<T> insert(@RequestBody SysUserEntity sysUser)  {
        log.info("openFeign调用成功");
        sysUser.setId(null);
        return sysUserService.editUser(sysUser);
    }
    /**
     * 编辑用户
     * @param sysUser
     * @param <T>
     * @return
     */
    @PutMapping("/{id}")
    public <T> R<T> editUser(@PathVariable("id") Integer id,@RequestBody SysUserEntity sysUser)  {
        sysUser.setId(id);
        return sysUserService.editUser(sysUser);
    }

    /**
     * 重置密码
     * @param <T>
     * @return
     */
    @PutMapping("resetPassword")
    public <T> R<T> resetPassword(@RequestParam("ids") String ids)  {
        if (Objects.isNull(ids)){
            return BR.genErrorResult("参数错误");
        }
        return sysUserService.resetPassword(ids);
    }
}
