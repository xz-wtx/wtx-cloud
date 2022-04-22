package com.cloud.order.service;

import com.cloud.common.response.R;
import com.cloud.goods.dto.OrderInfoDetailEntityDTO;
import java.util.List;

/**
 * <p>
 * 商品订单表 服务类
 * </p>
 *
 * @author wtx
 * @since 2022-04-20
 */
public interface OrderInfoService  {

    /**
     * 用户下单
     * @param infoDetail
     * @param <T>
     * @return
     */
    <T> R<T> placeOrder(List<OrderInfoDetailEntityDTO> infoDetail);
}
