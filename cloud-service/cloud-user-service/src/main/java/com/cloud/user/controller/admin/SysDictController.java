package com.cloud.user.controller.admin;

import com.cloud.common.response.BR;
import com.cloud.common.response.R;
import com.cloud.user.entity.SysDictEntity;
import com.cloud.user.entity.SysWhiteIpEntity;
import com.cloud.user.service.SysDictService;
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
@RequestMapping("/dict")
public class SysDictController extends BaseController{

    @Autowired
    SysDictService dictService;

    /**
     * 新增字典表
     * @param dictEntity
     * @param <T>
     * @return
     */
    @PostMapping("")
    public <T> R<T> insertDict(@RequestBody SysDictEntity dictEntity){
        dictEntity.setId(null);
        return dictService.editDict(dictEntity);
    }
    /**
     * 编辑字典表
     * @param dictEntity
     * @param <T>
     * @return
     */
    @PutMapping("/{id}")
    public <T> R<T> editDict(@PathVariable("id") Integer id,@RequestBody SysDictEntity dictEntity){
        dictEntity.setId(id);
        return dictService.editDict(dictEntity);
    }

    /**
     * 删除字典数据
     * @param id
     * @param <T>
     * @return
     */
    @DeleteMapping("/{id}")
    public <T> R<T> delDict(@PathVariable("id") Integer id){
        return dictService.delDict(id);
    }

    /**
     * 分页查询字典表
     * @param dictEntity
     * @param <T>
     * @return
     */
    @GetMapping("getPageList")
    public <T> R<T> getPageList(SysDictEntity dictEntity){

        return dictService.getPageList(dictEntity);
    }
}
