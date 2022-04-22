package com.cloud.goods.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author wtx
 * @since 2022-04-20
 */
@Getter
@Setter
@TableName("goods_info")
public class GoodsInfoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 租户id
     */
    private Integer tenantId;

    /**
     * 类型
     */
    private String type;

    /**
     * 名称
     */
    private String name;

    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String describe;

    /**
     * 主图
     */
    private String masterImg;
    /**
     * 多图
     */
    private String muchImg;

    /**
     * 内容
     */
    private String content;

    /**
     * 折扣价
     */
    private BigDecimal discountPrice;

    /**
     * 原价
     */
    private BigDecimal originalPrice;
    /**
     * 已购买总金额
     */
    private BigDecimal buyAmount;
    /**
     * 已购买总数
     */
    private Integer buyTotal;
    /**
     * 总数量
     */
    private Integer total;

    /**
     * 可用状态:0上架，1下架
     */
    private Integer status;

    /**
     * 是否删除：0否，1是
     */
    private Boolean deleteFlag;

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
