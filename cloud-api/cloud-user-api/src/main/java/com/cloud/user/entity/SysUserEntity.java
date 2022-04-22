package com.cloud.user.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author wtx
 * @since 2022-04-14
 */
@Getter
@Setter
@TableName("sys_user")
@Accessors(chain=true)
public class SysUserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 微信id
     */
    private String openId;

    /**
     * 账号
     */
    private String account;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 图像
     */
    private String img;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String mail;

    /**
     * 性别
     */
    private Boolean sex;

    /**
     * token
     */
    private String token;

    /**
     * 状态：0否 1是
     */
    private Integer status;

    /**
     * 是否删除：0否，1是
     */
    private Boolean deleteFlag;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLogin;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private String creator;

    /**
     * 修改人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updater;


}
