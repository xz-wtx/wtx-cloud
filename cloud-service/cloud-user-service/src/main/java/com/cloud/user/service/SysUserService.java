package com.cloud.user.service;

import com.cloud.user.dto.LoginDTO;
import com.cloud.user.dto.SysUserDTO;
import com.cloud.user.entity.SysUserEntity;
import com.cloud.common.response.R;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author wtx
 * @since 2022-04-11
 */
public interface SysUserService{


    /**
     * 用户登录
     * @param loginDTO
     * @return
     */
    <T> R<T>  login(LoginDTO loginDTO);

    /**
     * 编辑用户
     * @param sysUser
     * @param <T>
     * @return
     */
    <T> R<T> editUser(SysUserEntity sysUser);

    /**
     * 查询单个用户信息
     * @param id
     * @param <T>
     * @return
     */
    <T> R<T> getUser(Integer id);


    /**
     * 用户分页查询
     * @param sysUser
     * @param <T>
     * @return
     */
    <T> R<T> getPageUserList(SysUserDTO sysUser);
    /**
     * 通过token获取菜单
     * @return
     */
    R<String> getUserMenu();

    /**
     * 重置密码
     * @param <T>
     * @return
     */
    <T> R<T> resetPassword(String ids);
}
