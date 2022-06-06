package com.cloud.user.service;

import com.cloud.common.response.R;
import com.cloud.user.dto.SortDTO;
import com.cloud.user.entity.SysMenuEntity;

import java.util.List;

/**
 * <p>
 * 字典表 服务类
 * </p>
 *
 * @author wtx
 * @since 2022-04-26
 */
public interface SysMenuService {

    /**
     * 编辑菜单
     * @param sysMenuEntity
     * @param <T>
     * @return
     */
    <T> R<T> editMenu(SysMenuEntity sysMenuEntity);
    /**
     * 查询菜单
     * @param id)
     * @param <T>
     * @return
     */
    <T> R<T> queryMenu(Integer id);
    /**
     * 分页查询菜单
     * @param <T>
     * @return
     */
    <T> R<T> getTree(SysMenuEntity sysMenuEntity);
    /**
     * 分页查询菜单
     * @param <T>
     * @return
     */
    <T> R<T> getTreeList(SysMenuEntity sysMenuEntity);

    /**
     * 复制改目录及子目录
     * @param id
     * @param <T>
     * @return
     */
    <T> R<T> copyMenu(Integer id);

    /**
     * 查询菜单集合
     * @param sysMenuEntity
     * @return
     */
    List<SysMenuEntity> getList(SysMenuEntity sysMenuEntity);

    /**
     * 获取树形菜单
     * @param sortDTO
     * @param <T>
     * @return
     */
    <T> R<T> changeSort(SortDTO sortDTO);
}
