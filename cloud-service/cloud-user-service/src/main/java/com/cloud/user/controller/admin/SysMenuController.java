package com.cloud.user.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloud.common.response.BR;
import com.cloud.common.response.R;
import com.cloud.user.dto.SortDTO;
import com.cloud.user.entity.SysDeptEntity;
import com.cloud.user.entity.SysMenuEntity;
import com.cloud.user.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 字典表 前端控制器
 * </p>
 *
 * @author wtx
 * @since 2022-04-26
 */
@RestController
@RequestMapping("/menu")
public class SysMenuController extends BaseController{

    @Autowired
    SysMenuService menuService;

    /**
     * 编辑菜单
     * @param sysMenuEntity
     * @param <T>
     * @return
     */
    @PostMapping("")
    public <T> R<T> insertMenu(@RequestBody SysMenuEntity sysMenuEntity){
        sysMenuEntity.setId(null);
        return menuService.editMenu(sysMenuEntity);
    }
    /**
     * 编辑菜单
     * @param sysMenuEntity
     * @param <T>
     * @return
     */
    @PutMapping("/{id}")
    public <T> R<T> editMenu(@PathVariable("id") Integer id,@RequestBody SysMenuEntity sysMenuEntity){
        sysMenuEntity.setId(id);
        return menuService.editMenu(sysMenuEntity);
    }

    /**
     * 查询菜单
     * @param <T>
     * @return
     */
    @GetMapping("/{id}")
    public <T> R<T> queryMenu(@PathVariable("id") Integer id){

        return menuService.queryMenu(id);
    }
    /**
     * 复制改目录及子目录
     * @param id
     * @param <T>
     * @return
     */
    @PutMapping("copyMenu/{id}")
    public <T> R<T> copyMenu(@PathVariable("id") Integer id){

        return menuService.copyMenu(id);
    }

    /**
     * 分页查询菜单
     * @param sysMenuEntity
     * @param <T>
     * @return
     */
    @GetMapping("getTree")
    public <T> R<T> getTree(SysMenuEntity sysMenuEntity){

        return menuService.getTree(sysMenuEntity);
    }
    /**
     * 获取树形菜单
     * @param <T>
     * @return
     */
    @GetMapping("getTreeList")
    public <T> R<T> getTreeList(SysMenuEntity sysMenuEntity){
        return menuService.getTreeList(sysMenuEntity);
    }

    /**
     * 获取树形菜单
     * @param sortDTO
     * @param <T>
     * @return
     */
    @PutMapping("changeSort")
    public <T> R<T> changeSort(@RequestBody SortDTO sortDTO){
        return menuService.changeSort(sortDTO);
    }

}
