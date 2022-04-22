package com.cloud.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloud.goods.entity.GoodsInfoEntity;
import com.cloud.goods.mapper.GoodsInfoMapper;
import com.cloud.goods.service.GoodsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wtx
 * @since 2022-04-20
 */
@Service
public class GoodsInfoServiceImpl  implements GoodsInfoService {

    @Autowired
    GoodsInfoMapper goodsInfoMapper;

    @Override
    public List<GoodsInfoEntity> queryGoodsListById(List<Integer> ids,Boolean bool) {
        final QueryWrapper<GoodsInfoEntity> wrapper = new QueryWrapper<GoodsInfoEntity>().in("id", ids);
        if (bool){
            wrapper.eq("status",0).eq("delete_flag",0);
        }
        return goodsInfoMapper.selectList(wrapper);
    }

    @Override
    public List<GoodsInfoEntity> queryGoodsListByGoodsNo(List<String> goodsNos,Boolean bool) {
        final QueryWrapper<GoodsInfoEntity> wrapper = new QueryWrapper<GoodsInfoEntity>().in("goods_no", goodsNos);
        if (bool){
            wrapper.eq("status",0).eq("delete_flag",0);
        }
        return goodsInfoMapper.selectList(wrapper);
    }

    @Override
    public GoodsInfoEntity queryGoodsByGoodsNo(String goodsNo,Boolean bool) {
        final QueryWrapper<GoodsInfoEntity> wrapper = new QueryWrapper<GoodsInfoEntity>().eq("goods_no", goodsNo);
        if (bool){
            wrapper.eq("status",0).eq("delete_flag",0);
        }
        return goodsInfoMapper.selectOne(wrapper);
    }

    @Override
    public GoodsInfoEntity queryGoodsById(Integer id,Boolean bool) {
        final QueryWrapper<GoodsInfoEntity> wrapper = new QueryWrapper<GoodsInfoEntity>().eq("id", id);
        if (bool){
            wrapper.eq("status",0).eq("delete_flag",0);
        }
        return goodsInfoMapper.selectOne(wrapper);
    }

    @Override
    public Boolean updateGoodsInfo(GoodsInfoEntity goodsInfo) {
        return goodsInfoMapper.updateById(goodsInfo)>0;
    }

    @Override
    public Integer batchUpdateGoodsInfo(List<GoodsInfoEntity> goodsInfo) {
        AtomicReference<Integer> count= new AtomicReference<>(0);
        goodsInfo.forEach(p->{
            count.updateAndGet(v -> v + goodsInfoMapper.updateById(p));
        });
        return count.get();
    }
}
