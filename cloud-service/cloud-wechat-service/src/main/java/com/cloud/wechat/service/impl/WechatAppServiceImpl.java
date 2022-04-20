package com.cloud.wechat.service.impl;

import com.cloud.common.response.BR;
import com.cloud.common.response.R;
import com.cloud.wechat.entity.WechatAppEntity;
import com.cloud.wechat.mapper.WechatAppMapper;
import com.cloud.wechat.service.WechatAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wtx
 * @since 2022-04-16
 */
@Service
public class WechatAppServiceImpl implements WechatAppService {

    @Autowired
    WechatAppMapper wechatAppMapper;


    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public <T> R<T> insert(WechatAppEntity wechatApp) {
        wechatAppMapper.insert(wechatApp);
        return BR.genErrorResult("新增成功");
    }
}
