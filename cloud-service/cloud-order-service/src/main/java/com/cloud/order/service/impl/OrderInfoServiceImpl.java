package com.cloud.order.service.impl;

import com.cloud.common.response.BR;
import com.cloud.common.response.R;
import com.cloud.common.util.SnowflakeUtil;
import com.cloud.goods.dto.OrderInfoDetailEntityDTO;
import com.cloud.goods.entity.GoodsInfoEntity;
import com.cloud.goods.feign.GoodsFeign;
import com.cloud.order.entity.OrderInfoDetailEntity;
import com.cloud.order.entity.OrderInfoEntity;
import com.cloud.order.mapper.OrderInfoDetailMapper;
import com.cloud.order.mapper.OrderInfoMapper;
import com.cloud.order.service.OrderInfoService;
import com.cloud.spring.app.ServletUtils;
import com.cloud.spring.exception.CommonException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品订单表 服务实现类
 * </p>
 *
 * @author wtx
 * @since 2022-04-20
 */
@Service
public class OrderInfoServiceImpl implements OrderInfoService {

    @Autowired
    GoodsFeign goodsFeign;
    @Autowired
    OrderInfoMapper orderInfoMapper;
    @Autowired
    OrderInfoDetailMapper orderInfoDetailMapper;


    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public <T> R<T> placeOrder(List<OrderInfoDetailEntityDTO> infoDetail) {

        final List<Integer> ids = infoDetail.stream().map(OrderInfoDetailEntityDTO::getGoodsId).filter(Objects::nonNull).collect(Collectors.toList());
        //查询商品信息
        final List<GoodsInfoEntity> goodsList = goodsFeign.queryGoodsListById(ids,true);
        //转map格式
        final Map<Integer, GoodsInfoEntity> entityMap = goodsList.stream().collect(Collectors.toMap(GoodsInfoEntity::getId, p -> p));

        //封装数据
        List<OrderInfoDetailEntity> entityList=new ArrayList<>();
        BigDecimal transactionPrice=new BigDecimal("0");
        handleOrderInfoDetail(infoDetail, entityMap, entityList, transactionPrice);

        //保存订单信息
        final OrderInfoEntity orderInfo = new OrderInfoEntity() {{
            setGoodsNo(String.valueOf(SnowflakeUtil.nextId()));
            setDeleteFlag(false);
            setTenantId(goodsList.get(0).getTenantId());
            setStatus(0);
            setUserId(ServletUtils.getUser().getId());
        }};
        orderInfo.setTransactionPrice(transactionPrice);
        orderInfoMapper.insert(orderInfo);

        //保存订单明细信息
        entityList.forEach(p->{
            p.setOrderInfoId(orderInfo.getId());
            orderInfoDetailMapper.insert(p);
        });

        return BR.genSuccessResult();
    }

    //遍历封装数据
    private void handleOrderInfoDetail(List<OrderInfoDetailEntityDTO> infoDetail, Map<Integer, GoodsInfoEntity> entityMap, List<OrderInfoDetailEntity> entityList, BigDecimal transactionPrice) {
        for (OrderInfoDetailEntityDTO entityDTO : infoDetail) {
            OrderInfoDetailEntity entity=new OrderInfoDetailEntity();
            BeanUtils.copyProperties(entityDTO,entity);
            final GoodsInfoEntity goodsInfo = entityMap.get(entity.getGoodsId());
            if (Objects.isNull(goodsInfo)){
                throw new CommonException("商品:"+entity.getGoodsName()+",不存在或已下架");
            }
            if (Objects.isNull(entity.getBuyTotal())){
                throw new CommonException("商品:"+entity.getGoodsName()+",没有选择购买数量");
            }
            if (entity.getBuyTotal()+goodsInfo.getBuyTotal()>goodsInfo.getTotal()) {
                throw new CommonException("商品:"+goodsInfo.getName()+",库存不足");
            }
            //获取每个商品购买金额
            entity.setOriginalPrice(goodsInfo.getOriginalPrice());
            entity.setDiscountPrice(goodsInfo.getDiscountPrice());
            entity.setGoodsImg(goodsInfo.getMasterImg());
            entity.setGoodsName(goodsInfo.getName());
            entityList.add(entity);
            //统计整个订单支付金额
            transactionPrice = transactionPrice.add(entity.getDiscountPrice());
        }
    }

}
