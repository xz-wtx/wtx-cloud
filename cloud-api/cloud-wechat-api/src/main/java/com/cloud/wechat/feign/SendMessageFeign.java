package com.cloud.wechat.feign;

import com.cloud.common.constant.ServicePrefixConstant;
import com.cloud.wechat.dto.WechatSendDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author wtx
 * @Description TODO 微信发送消息
 * @Date 2022/4/8 12:06
 * @Version 1.0
 */
@FeignClient("wechat")
public interface SendMessageFeign {


    /**
     * 发送消息
     * @param wechatSendDTO
     */
    @GetMapping(ServicePrefixConstant.WECHAT_SERVICE +"send/msg")
     void sendMsg(@RequestBody WechatSendDTO wechatSendDTO);

}
