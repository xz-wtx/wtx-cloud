package com.cloud.user.controller.admin;

import com.cloud.common.response.BR;
import com.cloud.common.response.R;
import com.cloud.user.entity.SysWhitePathEntity;
import com.cloud.user.service.SysWhitePathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 系统全部请求路径 前端控制器
 * </p>
 *
 * @author wtx
 * @since 2022-04-26
 */
@RestController
@RequestMapping("/whitePath")
public class SysWhitePathController extends BaseController{

    @Autowired
    SysWhitePathService whitePathService;
    /**
     * 新增白名单路径
     * @param sysWhitePath
     * @param <T>
     * @return
     */
    @PostMapping("")
    public <T> R<T> insertWhitePath(@RequestBody SysWhitePathEntity sysWhitePath){
        sysWhitePath.setId(null);
        return whitePathService.editWhitePath(sysWhitePath);
    }
    /**
     * 编辑白名单路径
     * @param sysWhitePath
     * @param <T>
     * @return
     */
    @PutMapping("/{id}")
    public <T> R<T> editWhitePath(@PathVariable("id") Integer id,@RequestBody SysWhitePathEntity sysWhitePath){
        sysWhitePath.setId(id);
        return whitePathService.editWhitePath(sysWhitePath);
    }
    /**
     * 删除路径
     * @param sysWhitePath
     * @param <T>
     * @return
     */
    @DeleteMapping("/{id}")
    public <T> R<T> delPath(@PathVariable("id") Integer id,@RequestBody SysWhitePathEntity sysWhitePath){
        sysWhitePath.setId(id);
        return whitePathService.delPath(sysWhitePath);
    }

    /**
     * 分页查询白名单路径
     * @param sysWhitePath
     * @param <T>
     * @return
     */
    @GetMapping("getPageList")
    public <T> R<T> getPageList(SysWhitePathEntity sysWhitePath){

        return whitePathService.getPageList(sysWhitePath);
    }
}
