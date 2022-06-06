package com.cloud.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloud.common.constant.BaseConstant;
import com.cloud.common.constant.RedisConstant;
import com.cloud.common.response.BR;
import com.cloud.common.response.R;
import com.cloud.common.util.VerifyUtils;
import com.cloud.common.util.WrapperUtils;
import com.cloud.user.entity.SysWhitePathEntity;
import com.cloud.user.mapper.SysWhitePathMapper;
import com.cloud.user.service.SysWhitePathService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统全部请求路径 服务实现类
 * </p>
 *
 * @author wtx
 * @since 2022-04-26
 */
@Service
public class SysWhitePathServiceImpl implements SysWhitePathService {

    @Autowired
    SysWhitePathMapper whitePathMapper;
    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public <T> R<T> editWhitePath(SysWhitePathEntity sysWhitePath) {
        if (Objects.isNull(sysWhitePath.getId())){
            whitePathMapper.insert(sysWhitePath);
        }else{
            VerifyUtils.isEmpty(whitePathMapper.selectById(sysWhitePath.getId()));
            whitePathMapper.updateById(sysWhitePath);
        }
        initPath();
        return BR.genSuccessResult();
    }

    @Override
    @Transactional
    public R delPath(SysWhitePathEntity sysWhitePath) {

        whitePathMapper.deleteById(sysWhitePath.getId());
        initPath();
        return BR.genSuccessResult();
    }


    void initPath(){
        final List<SysWhitePathEntity> entities = whitePathMapper.selectList(new QueryWrapper<>());
        if (CollectionUtils.isNotEmpty(entities)){
            final List<String> list = entities.stream().map(SysWhitePathEntity::getPath).collect(Collectors.toList());
            redisTemplate.opsForValue().set(RedisConstant.WHITE_PATH, JSONArray.toJSONString(list));
        }
    }


    @Override
    public R getPageList(SysWhitePathEntity sysWhitePath) {
        final QueryWrapper<SysWhitePathEntity> wrapper = WrapperUtils.getWrapper(SysWhitePathEntity.class);
        wrapper.like(StringUtils.isNotBlank(sysWhitePath.getPath()),"path",sysWhitePath.getPath());
        wrapper.like(StringUtils.isNotBlank(sysWhitePath.getRemark()),"remark",sysWhitePath.getRemark());
        final Page<SysWhitePathEntity> entityPage = whitePathMapper.selectPage(WrapperUtils.page(sysWhitePath), wrapper);
        return BR.genSuccessResult(entityPage);
    }
}
