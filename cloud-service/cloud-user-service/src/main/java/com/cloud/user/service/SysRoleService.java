package com.cloud.user.service;

import com.cloud.common.response.R;
import com.cloud.user.entity.SysRoleEntity;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author wtx
 * @since 2022-04-26
 */
public interface SysRoleService  {

    /**
     * 编辑角色
     * @param sysRoleEntity
     * @param <T>
     * @return
     */
    <T> R<T> editRole(SysRoleEntity sysRoleEntity);


    /**
     * 分页查询角色
     * @param sysRoleEntity
     * @param <T>
     * @return
     */
    <T> R<T> getPageList(SysRoleEntity sysRoleEntity);

    /**
     * 查询角色列表
     * @param sysRoleEntity
     * @param <T>
     * @return
     */
    <T> R<T> getList(SysRoleEntity sysRoleEntity);

    /**
     * 分配菜单
     * @param sysRoleEntity
     * @param <T>
     * @return
     */
    <T> R<T> allotMenu(SysRoleEntity sysRoleEntity);
}
