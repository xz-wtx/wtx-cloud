package com.cloud.goods.dto;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

/**
 * @author 60003404
 * @Description TODO
 * @Date 2022/4/20 17:06
 * @Version 1.0
 */
@Log4j2
@Data
public class OrderInfoDetailEntityDTO {

    /**
     * 商品id
     */
    private Integer goodsId;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 购买数量
     */
    private Integer buyTotal;
}
