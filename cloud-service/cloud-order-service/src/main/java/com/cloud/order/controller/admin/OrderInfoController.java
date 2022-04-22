package com.cloud.order.controller.admin;

import com.cloud.common.response.BR;
import com.cloud.common.response.R;
import com.cloud.goods.dto.OrderInfoDetailEntityDTO;
import com.cloud.order.service.OrderInfoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 60003404
 * @Description TODO 用户下单
 * @Date 2022/4/20 14:29
 * @Version 1.0
 */

@Log4j2
@RestController
@RequestMapping("order")
public class OrderInfoController {

    @Autowired
    OrderInfoService orderInfoService;

    @RequestMapping("placeOrder")
    public <T>R<T> placeOrder(@RequestBody List<OrderInfoDetailEntityDTO> infoDetail){
        if (CollectionUtils.isEmpty(infoDetail)){
            return BR.genErrorResult("请先选择订单");
        }
        final List<Integer> ids = infoDetail.stream().map(OrderInfoDetailEntityDTO::getGoodsId).filter(Objects::nonNull).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(ids)){
            return BR.genErrorResult("请先选择订单");
        }
       return orderInfoService.placeOrder(infoDetail);
    }
}
