package com.cloud.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.cloud.common.dto.PageDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author wtx
 * @since 2022-04-26
 */
@Getter
@Setter
@TableName("sys_dept")
public class SysDeptEntity  extends PageDTO implements Serializable  {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 上级id:0父级
     */
    private Integer parentId;

    /**
     * 级别:0顶级
     */
    private Integer level;

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


    /*******树形********/
    @TableField(exist = false)
    List<SysDeptEntity> children;
}
