package com.cloud.wechat.controller.api;

import com.cloud.common.response.R;
import com.cloud.wechat.dto.WechatSendDTO;
import com.cloud.wechat.service.SendMessageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wtx
 * @Description TODO
 * @Date 2022/4/18 20:23
 * @Version 1.0
 */
@Log4j2
@RestController
@RequestMapping("/send")
public class SendMessageFeignService{

    @Autowired
    SendMessageService sendMessageService;


    /**
     * 发送消息
     * @param wechatSendDTO
     * @param <T>
     * @return
     */
    @RequestMapping("msg")
    public <T> R<T> sendMsg(WechatSendDTO wechatSendDTO){
        return sendMessageService.send(wechatSendDTO);
    }
}
