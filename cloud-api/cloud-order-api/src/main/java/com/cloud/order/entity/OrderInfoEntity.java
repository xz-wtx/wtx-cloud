package com.cloud.order.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 商品订单表
 * </p>
 *
 * @author wtx
 * @since 2022-04-20
 */
@Getter
@Setter
@TableName("order_info")
public class OrderInfoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 商品编号
     */
    private String goodsNo;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 租户id
     */
    private Integer tenantId;

    /**
     * 成交价
     */
    private BigDecimal transactionPrice;

    /**
     * 支付时间
     */
    private LocalDateTime payTime;

    /**
     * 状态:0待支付，1待发货，2待签收，3已完成，4退货
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
