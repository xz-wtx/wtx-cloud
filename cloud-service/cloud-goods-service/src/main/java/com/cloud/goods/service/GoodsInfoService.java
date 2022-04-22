package com.cloud.goods.service;

import com.cloud.goods.entity.GoodsInfoEntity;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wtx
 * @since 2022-04-20
 */
public interface GoodsInfoService  {
    /**
     * 根据多个id查询商品信息列表
     * @param ids
     * @param bool 是否查询可用的订单
     * @return
     */
    List<GoodsInfoEntity> queryGoodsListById(List<Integer> ids,Boolean bool);

    /**
     * 根据多个goodsNos查询商品信息列表
     * @param goodsNos
     * @param bool 是否查询可用的订单
     * @return
     */
    List<GoodsInfoEntity> queryGoodsListByGoodsNo(List<String> goodsNos,Boolean bool);

    /**
     * 根据goodsNo查询商品信息
     * @param goodsNo
     * @param bool 是否查询可用的订单
     * @return
     */
    GoodsInfoEntity queryGoodsByGoodsNo(String goodsNo,Boolean bool);
    /**
     * 根据id查询商品信息
     * @param id
     * @param bool 是否查询可用的订单
     * @return
     */
    GoodsInfoEntity queryGoodsById(Integer id,Boolean bool);
    /**
     * 修改商品信息
     * @param goodsInfo
     * @return
     */
    Boolean updateGoodsInfo(GoodsInfoEntity goodsInfo);

    /**
     * 修改商品信息
     * @param goodsInfo
     * @return
     */
    Integer batchUpdateGoodsInfo(List<GoodsInfoEntity> goodsInfo);
}
