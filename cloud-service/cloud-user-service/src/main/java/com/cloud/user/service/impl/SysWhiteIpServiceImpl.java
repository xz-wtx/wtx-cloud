package com.cloud.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloud.common.response.BR;
import com.cloud.common.response.R;
import com.cloud.common.util.VerifyUtils;
import com.cloud.common.util.WrapperUtils;
import com.cloud.user.entity.SysWhiteIpEntity;
import com.cloud.user.mapper.SysWhiteIpMapper;
import com.cloud.user.service.SysWhiteIpService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wtx
 * @since 2022-04-26
 */
@Service
public class SysWhiteIpServiceImpl implements SysWhiteIpService {

    @Autowired
    SysWhiteIpMapper whiteIpMapper;

    @Override
    public <T> R<T> editWhiteIp(SysWhiteIpEntity sysWhiteIp) {
        if (sysWhiteIp.getId()==null){
            whiteIpMapper.insert(sysWhiteIp);
        }else{
            VerifyUtils.isEmpty( whiteIpMapper.selectById(sysWhiteIp.getId()));
            whiteIpMapper.updateById(sysWhiteIp);
        }
        return BR.genSuccessResult();
    }

    @Override
    public R delIp(Integer id) {
        whiteIpMapper.deleteById(id);
        return BR.genSuccessResult();
    }

    @Override
    public R getPageList(SysWhiteIpEntity sysWhiteIp) {
        final QueryWrapper<SysWhiteIpEntity> wrapper = WrapperUtils.getWrapper(SysWhiteIpEntity.class);
        wrapper.like(StringUtils.isNotBlank(sysWhiteIp.getIp()),"ip",sysWhiteIp.getIp());
        wrapper.like(StringUtils.isNotBlank(sysWhiteIp.getRemark()),"remark",sysWhiteIp.getRemark());
        final Page<SysWhiteIpEntity> entityPage = whiteIpMapper.selectPage(WrapperUtils.page(sysWhiteIp), wrapper);
        return BR.genSuccessResult(entityPage);
    }
}
