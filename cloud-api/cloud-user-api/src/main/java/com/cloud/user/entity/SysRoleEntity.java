package com.cloud.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cloud.common.dto.PageDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author wtx
 * @since 2022-04-26
 */
@Getter
@Setter
@TableName("sys_role")
public class SysRoleEntity extends PageDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色编号
     */
    private String code;
    /**
     * 状态:（0正常 1停用）
     */
    private Integer status;

    /**
     * 是否删除：0否，1是
     */
    private Integer deleteFlag;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 修改人
     */
    private String updater;


     /******扩展字段******/
     @TableField(exist = false)
    private ArrayList<Integer> menuIds;

    @TableField(exist = false)
    private List<Map<String, Object>> listMap;
}
