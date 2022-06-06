package com.cloud.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.cloud.common.dto.PageDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 字典表
 * </p>
 *
 * @author wtx
 * @since 2022-04-26
 */
@Getter
@Setter
@TableName("sys_menu")
public class SysMenuEntity extends PageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 菜单权限编号
     */
    private String authCode;

    /**
     * 菜单名称
     */
    private String title;
    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 父级id:默认0
     */
    private Integer parentId;

    /**
     *vue文件路径
     */
    private String filePath;

    /**
     * 组件名称
     */
    private String name;

    /**
     * 菜单类型：(0目录,1菜单,2按钮)
     */
    private Integer type;

    /**
     * 是否缓存（0不缓存 1缓存）
     */
    private Integer isCache;

    /**
     * 菜单可见:（0显示 1隐藏）
     */
    private Integer visible;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 菜单状态（0正常 1停用）
     */
    private Integer status;

    /**
     * 是否删除：(0否，1是)
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


    /***菜单子级****/
    @TableField(exist = false)
    private List<SysMenuEntity> children;

    @TableField(exist = false)
    private List<Integer> types;
}
