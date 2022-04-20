package com.cloud.wechat.service;

import com.cloud.common.response.R;
import com.cloud.wechat.dto.WechatSendDTO;

/**
 * @author wtx
 * @Description TODO
 * @Date 2022/4/18 20:49
 * @Version 1.0
 */
public interface SendMessageService {
    /**
     * 发送消息
     * @param wechatSendDTO
     * @param <T>
     * @return
     */
     <T> R<T> send(WechatSendDTO wechatSendDTO);
}
