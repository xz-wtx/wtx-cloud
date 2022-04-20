package com.cloud.user.service;


import com.cloud.user.dto.LoginDTO;
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
     * 新增用户
     * @param sysUserEntity
     * @param <T>
     * @return
     */
    <T> R<T> insert(SysUserEntity sysUserEntity) ;
    /**
     * 用户登录
     * @param loginDTO
     * @return
     */
    <T> R<T>  login(LoginDTO loginDTO);
}
