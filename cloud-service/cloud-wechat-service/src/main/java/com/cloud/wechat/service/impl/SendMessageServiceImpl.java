package com.cloud.wechat.service.impl;

import com.cloud.common.response.BR;
import com.cloud.common.response.R;
import com.cloud.wechat.dto.WechatSendDTO;
import com.cloud.wechat.service.SendMessageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/**
 * @author wtx
 * @Description TODO
 * @Date 2022/4/18 20:49
 * @Version 1.0
 */
@Log4j2
@Service
public class SendMessageServiceImpl implements SendMessageService {

    @Override
    public <T> R<T> send(WechatSendDTO wechatSendDTO) {
        log.info("发送消息");
        return BR.genDefSuccessResult("发送成功");
    }
}
