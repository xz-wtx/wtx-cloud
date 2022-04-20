package com.cloud.wechat.service;

import com.cloud.common.response.R;
import com.cloud.wechat.entity.WechatAppEntity;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wtx
 * @since 2022-04-16
 */
public interface WechatAppService {

    /**
     * 新增wechatApp配置
     * @param wechatApp
     * @return
     */
    <T> R<T> insert(WechatAppEntity wechatApp);

}
