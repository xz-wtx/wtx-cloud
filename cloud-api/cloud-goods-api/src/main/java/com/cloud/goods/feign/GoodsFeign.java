package com.cloud.goods.feign;

import com.cloud.common.constant.ServicePrefixConstant;
import com.cloud.goods.entity.GoodsInfoEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author 60003404
 * @Description TODO
 * @Date 2022/4/20 14:45
 * @Version 1.0
 */
@FeignClient(ServicePrefixConstant.GOODS_SERVICE)
public interface GoodsFeign {

    /**
     * 根据多个id查询商品信息列表
     * @param ids
     * @param bool 是否查询可用的订单
     * @return
     */
    @RequestMapping(ServicePrefixConstant.GOODS_SERVICE+"/feign/goods/queryGoodsListById")
    List<GoodsInfoEntity> queryGoodsListById(@RequestParam("ids") List<Integer> ids, @RequestParam("bool") Boolean bool);

    /**
     * 根据多个goodsNos查询商品信息列表
     * @param goodsNos
     * @param bool 是否查询可用的订单
     * @return
     */
    @RequestMapping(ServicePrefixConstant.GOODS_SERVICE+"goods/feign/goods/queryGoodsListByGoodsNo")
    List<GoodsInfoEntity> queryGoodsListByGoodsNo(@RequestParam("goodsNos") List<String> goodsNos,@RequestParam("bool")Boolean bool);

    /**
     * 根据goodsNo查询商品信息
     * @param goodsNo
     * @param bool 是否查询可用的订单
     * @return
     */
    @RequestMapping(ServicePrefixConstant.GOODS_SERVICE+"goods/feign/goods/queryGoodsByGoodsNo")
    GoodsInfoEntity queryGoodsByGoodsNo(@RequestParam("goodsNo") String goodsNo,@RequestParam("bool") Boolean bool);
    /**
     * 根据id查询商品信息
     * @param id
     * @param bool 是否查询可用的订单
     * @return
     */
    @RequestMapping(ServicePrefixConstant.GOODS_SERVICE+"goods/feign/goods/queryGoodsById")
    GoodsInfoEntity queryGoodsById(@RequestParam("id") Integer id,@RequestParam("bool") Boolean bool);
    /**
     * 修改商品信息
     * @param goodsInfo
     * @return
     */
    @RequestMapping(ServicePrefixConstant.GOODS_SERVICE+"goods/feign/goods/updateGoodsInfo")
    Boolean updateGoodsInfo(@RequestBody GoodsInfoEntity goodsInfo);

    /**
     * 修改商品信息
     * @param goodsInfo
     * @return
     */
    @RequestMapping(ServicePrefixConstant.GOODS_SERVICE+"goods/feign/goods/updateGoodsInfo")
    Integer batchUpdateGoodsInfo(@RequestBody List<GoodsInfoEntity> goodsInfo);
}
