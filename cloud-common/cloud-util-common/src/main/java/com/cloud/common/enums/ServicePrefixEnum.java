package com.cloud.common.enums;

import com.cloud.common.constant.ServicePrefixConstant;
import lombok.extern.log4j.Log4j2;

/**
 * @author 60003404
 * @Description TODO
 * @Date 2022/4/19 13:45
 * @Version 1.0
 */
@Log4j2
public enum ServicePrefixEnum {
    user("user", ServicePrefixConstant.USER_SERVICE),
    wechat("wechat", ServicePrefixConstant.WECHAT_SERVICE)
    ;
    public String serviceName;

    public String prefix;

    ServicePrefixEnum(String serviceName,String prefix){
        this.serviceName=serviceName;
        this.prefix=prefix;
    }

    public String getPrefix(){
         return this.prefix;
    }
}
