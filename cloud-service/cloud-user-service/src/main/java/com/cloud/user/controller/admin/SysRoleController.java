package com.cloud.user.controller.admin;

import com.cloud.common.response.BR;
import com.cloud.common.response.R;
import com.cloud.user.entity.SysRoleEntity;
import com.cloud.user.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author wtx
 * @since 2022-04-26
 */
@RestController
@RequestMapping("/role")
public class SysRoleController extends BaseController{

    @Autowired
    SysRoleService roleService;

    /**
     * 编辑角色
     * @param sysRoleEntity
     * @param <T>
     * @return
     */
    @PostMapping("")
    public <T> R<T> insertRole(@RequestBody SysRoleEntity sysRoleEntity){
        sysRoleEntity.setId(null);
        return roleService.editRole(sysRoleEntity);
    }
    /**
     * 编辑角色
     * @param sysRoleEntity
     * @param <T>
     * @return
     */
    @PutMapping("/{id}")
    public <T> R<T> editRole(@PathVariable("id") Integer id,@RequestBody SysRoleEntity sysRoleEntity){
        sysRoleEntity.setId(id);
        return roleService.editRole(sysRoleEntity);
    }

    /**
     * 分页查询角色
     * @param sysRoleEntity
     * @param <T>
     * @return
     */
    @GetMapping("getPageList")
    public <T> R<T> getPageList(SysRoleEntity sysRoleEntity){

        return roleService.getPageList(sysRoleEntity);
    }
    /**
     * 查询角色列表
     * @param sysRoleEntity
     * @param <T>
     * @return
     */
    @GetMapping("getList")
    public <T> R<T> getList(SysRoleEntity sysRoleEntity){

        return roleService.getList(sysRoleEntity);
    }
    /**
     * 分配菜单
     * @param sysRoleEntity
     * @param <T>
     * @return
     */
    @PutMapping("allotMenu/{id}")
    public <T> R<T> allotMenu(@PathVariable("id") Integer id,@RequestBody SysRoleEntity sysRoleEntity){
        sysRoleEntity.setId(id);
        return roleService.allotMenu(sysRoleEntity);
    }
}
