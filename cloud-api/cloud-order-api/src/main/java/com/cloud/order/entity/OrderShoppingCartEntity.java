package com.cloud.order.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 商品购物车
 * </p>
 *
 * @author wtx
 * @since 2022-04-20
 */
@Getter
@Setter
@TableName("order_shopping_cart")
public class OrderShoppingCartEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 租户id
     */
    private Integer tenantId;

    /**
     * 商品id
     */
    private Integer goodsId;

    /**
     * 选购数量
     */
    private Integer pickTotal;

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
