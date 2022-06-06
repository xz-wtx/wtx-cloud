package com.cloud.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloud.common.constant.BaseConstant;
import com.cloud.common.response.BR;
import com.cloud.common.response.R;
import com.cloud.common.util.VerifyUtils;
import com.cloud.common.util.WrapperUtils;
import com.cloud.user.entity.SysDictEntity;
import com.cloud.user.mapper.SysDictMapper;
import com.cloud.user.service.SysDictService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author wtx
 * @since 2022-04-26
 */
@Service
public class SysDictServiceImpl implements SysDictService {

    @Autowired
    SysDictMapper dictMapper;

    @Override
    public <T> R<T> editDict(SysDictEntity dictEntity) {
        if (Objects.isNull(dictEntity.getId())){
            dictMapper.insert(dictEntity);
        }else{
            VerifyUtils.isEmpty(dictMapper.selectById(dictEntity.getId()));
            dictMapper.updateById(dictEntity);
        }
        return BR.genSuccessResult();
    }

    @Override
    public R delDict(Integer id) {
            dictMapper.updateById(new SysDictEntity(){{
                setDeleteFlag(1);
                setId(id);
            }});
        return BR.genSuccessResult();
    }

    @Override
    public R getPageList(SysDictEntity dictEntity) {
        final QueryWrapper<SysDictEntity> wrapper = WrapperUtils.getWrapper(SysDictEntity.class);
        wrapper.like(StringUtils.isNotBlank(dictEntity.getCode()),"code",dictEntity.getCode());
        wrapper.like(StringUtils.isNotBlank(dictEntity.getRemark()),"remark",dictEntity.getRemark());
        wrapper.like(StringUtils.isNotBlank(dictEntity.getKey()),"`key`",dictEntity.getKey());
        wrapper.like(StringUtils.isNotBlank(dictEntity.getValue()),"`value`",dictEntity.getValue());
        final Page<SysDictEntity> entityPage = dictMapper.selectPage(WrapperUtils.page(dictEntity), wrapper);
        return BR.genSuccessResult(entityPage);
    }
}
