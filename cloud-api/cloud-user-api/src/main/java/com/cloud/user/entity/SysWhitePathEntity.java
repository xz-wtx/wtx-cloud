package com.cloud.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;

import com.cloud.common.dto.PageDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 系统全部请求路径
 * </p>
 *
 * @author wtx
 * @since 2022-04-26
 */
@Getter
@Setter
@TableName("sys_white_path")
public class SysWhitePathEntity extends PageDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 路径
     */
    private String path;

    /**
     * 备注
     */
    private String remark;
}
