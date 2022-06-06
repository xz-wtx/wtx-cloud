package com.cloud.user.controller.admin;

import com.cloud.common.response.R;
import com.cloud.user.entity.SysDeptEntity;
import com.cloud.user.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wtx
 * @since 2022-04-26
 */
@RestController
@RequestMapping("/dept")
public class SysDeptController extends BaseController{

    @Autowired
    SysDeptService sysDeptService;

    /**
     * 编辑部门
     * @param sysDeptEntity
     * @param <T>
     * @return
     */
    @PostMapping("")
    public <T> R<T> insertDept(@RequestBody SysDeptEntity sysDeptEntity){
        sysDeptEntity.setId(null);
        return sysDeptService.editDept(sysDeptEntity);
    }

    /**
     * 编辑部门
     * @param sysDeptEntity
     * @param <T>
     * @return
     */
    @PutMapping("/{id}")
    public <T> R<T> editDept(@PathVariable("id") Integer id,@RequestBody SysDeptEntity sysDeptEntity){
        sysDeptEntity.setId(id);
        return sysDeptService.editDept(sysDeptEntity);
    }

    /**
     * 查询部门
     * @param sysDeptEntity
     * @param <T>
     * @return
     */
    @GetMapping("/{id}")
    public <T> R<T> queryDept(@PathVariable("id") Integer id,SysDeptEntity sysDeptEntity){
        sysDeptEntity.setId(id);
        return sysDeptService.queryDept(sysDeptEntity);
    }

    /**
     * 获取树形部门(简化)
     * @param sysDeptEntity
     * @param <T>
     * @return
     */
    @GetMapping("getTree")
    public <T> R<T> getTree(SysDeptEntity sysDeptEntity){

        return sysDeptService.getTree(sysDeptEntity);
    }
    /**
     * 获取树形部门
     * @param sysDeptEntity
     * @param <T>
     * @return
     */
    @GetMapping("getTreeList")
    public <T> R<T> getTreeList(SysDeptEntity sysDeptEntity){

        return sysDeptService.getTreeList(sysDeptEntity);
    }

}
