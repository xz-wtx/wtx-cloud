package com.cloud.goods.controller.feign;

import com.cloud.goods.entity.GoodsInfoEntity;
import com.cloud.goods.service.GoodsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wtx
 * @since 2022-04-20
 */
@RestController
@RequestMapping("/feign/goods")
public class GoodsInfoFeignService  {

    @Autowired
    GoodsInfoService goodsInfoService;

    /**
     * 根据多个id查询商品信息列表
     * @param ids
     * @param bool 是否查询可用的订单
     * @return
     */
    @RequestMapping("queryGoodsListById")
    public List<GoodsInfoEntity> queryGoodsListById(@RequestParam("ids") List<Integer> ids,@RequestParam("bool") Boolean bool) {
        return goodsInfoService.queryGoodsListById(ids,bool);
    }

    /**
     * 根据多个goodsNos查询商品信息列表
     * @param goodsNos
     * @param bool 是否查询可用的订单
     * @return
     */
    @RequestMapping("queryGoodsListByGoodsNo")
    public List<GoodsInfoEntity> queryGoodsListByGoodsNo(@RequestParam("goodsNos") List<String> goodsNos,@RequestParam("bool")Boolean bool) {
        return goodsInfoService.queryGoodsListByGoodsNo(goodsNos,bool);
    }

    /**
     * 根据goodsNo查询商品信息
     * @param goodsNo
     * @param bool 是否查询可用的订单
     * @return
     */
    @RequestMapping("queryGoodsByGoodsNo")
    public GoodsInfoEntity queryGoodsByGoodsNo(@RequestParam("goodsNo") String goodsNo,@RequestParam("bool") Boolean bool) {
        return goodsInfoService.queryGoodsByGoodsNo(goodsNo,bool);
    }

    /**
     * 根据id查询商品信息
     * @param id
     * @param bool 是否查询可用的订单
     * @return
     */
    @RequestMapping("queryGoodsById")
    public GoodsInfoEntity queryGoodsById(@RequestParam("id") Integer id,@RequestParam("bool") Boolean bool) {
        return goodsInfoService.queryGoodsById(id,bool);
    }

    /**
     * 修改商品信息
     * @param goodsInfo
     * @return
     */
    @RequestMapping("updateGoodsInfo")
    public Boolean updateGoodsInfo(@RequestBody GoodsInfoEntity goodsInfo) {
        return goodsInfoService.updateGoodsInfo(goodsInfo);
    }

    /**
     * 修改商品信息
     * @param goodsInfo
     * @return
     */
    @RequestMapping("batchUpdateGoodsInfo")
    public Integer batchUpdateGoodsInfo(@RequestBody List<GoodsInfoEntity> goodsInfo) {
        return goodsInfoService.batchUpdateGoodsInfo(goodsInfo);
    }
}
