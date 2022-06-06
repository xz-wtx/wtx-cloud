package com.cloud.user.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.cloud.common.dto.PageDTO;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

/**
 * @author 60003404
 * @Description TODO
 * @Date 2022/4/26 16:24
 * @Version 1.0
 */
@Log4j2
@Data
public class SysUserDTO extends PageDTO {

    private Integer id;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 部门ID
     */
    private Integer deptId;

    /**
     * 账号
     */
    private String account;

    /**
     * 状态:（0正常 1停用）
     */
    private Integer status;

    /**角色id****/
    @TableField(exist = false)
    public Integer[] roleList;
}
