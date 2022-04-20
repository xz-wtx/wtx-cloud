package com.cloud.wechat.controller.api;

import com.cloud.common.response.R;
import com.cloud.wechat.entity.WechatAppEntity;
import com.cloud.wechat.service.WechatAppService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wtx
 * @Description TODO
 * @Date 2022/4/18 20:44
 * @Version 1.0
 */
@Log4j2
@RestController
@RequestMapping("/wechat/app")
public class WechatAppFeignService {

    @Autowired
    WechatAppService wechatAppService;

    /**
     * 保存wechatApp配置
     * @param wechatApp
     * @param <T>
     * @return
     */
    @RequestMapping("insert")
    public <T> R<T> insert(WechatAppEntity wechatApp){
        return wechatAppService.insert(wechatApp);
    }
}
