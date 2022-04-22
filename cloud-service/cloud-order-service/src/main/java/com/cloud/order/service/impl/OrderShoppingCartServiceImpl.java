package com.cloud.order.service.impl;

import com.cloud.order.entity.OrderShoppingCartEntity;
import com.cloud.order.mapper.OrderShoppingCartMapper;
import com.cloud.order.service.OrderShoppingCartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品购物车 服务实现类
 * </p>
 *
 * @author wtx
 * @since 2022-04-20
 */
@Service
public class OrderShoppingCartServiceImpl extends ServiceImpl<OrderShoppingCartMapper, OrderShoppingCartEntity> implements OrderShoppingCartService {

}
