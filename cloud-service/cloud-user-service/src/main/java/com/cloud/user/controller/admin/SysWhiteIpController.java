package com.cloud.user.controller.admin;

import com.cloud.common.response.BR;
import com.cloud.common.response.R;
import com.cloud.user.entity.SysRoleEntity;
import com.cloud.user.entity.SysWhiteIpEntity;
import com.cloud.user.service.SysWhiteIpService;
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
@RequestMapping("/whiteIp")
public class SysWhiteIpController extends BaseController{

    @Autowired
    SysWhiteIpService whiteIpService;

    /**
     * 新增白名单ip
     * @param sysWhiteIp
     * @param <T>
     * @return
     */
    @PostMapping("")
    public <T> R<T> editWhiteIp(@RequestBody SysWhiteIpEntity sysWhiteIp){
        sysWhiteIp.setId(null);
        return whiteIpService.editWhiteIp(sysWhiteIp);
    }
    /**
     * 编辑白名单ip
     * @param sysWhiteIp
     * @param <T>
     * @return
     */
    @PutMapping("/{id}")
    public <T> R<T> editWhiteIp(@PathVariable("id") Integer id,@RequestBody SysWhiteIpEntity sysWhiteIp){
        sysWhiteIp.setId(id);
        return whiteIpService.editWhiteIp(sysWhiteIp);
    }
    /**
     * 查询白名单ip
     * @param <T>
     * @return
     */
    @DeleteMapping("/{id}")
    public <T> R<T> delWhiteIp(@PathVariable("id") Integer id){

        return whiteIpService.delIp(id);
    }

    /**
     * 分页查询白名单ip
     * @param sysWhiteIp
     * @param <T>
     * @return
     */
    @GetMapping("getPageList")
    public <T> R<T> getPageList(SysWhiteIpEntity sysWhiteIp){

        return whiteIpService.getPageList(sysWhiteIp);
    }
}
