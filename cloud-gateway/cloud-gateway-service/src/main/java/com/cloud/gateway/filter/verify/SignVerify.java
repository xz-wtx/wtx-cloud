package com.cloud.gateway.filter.verify;

import com.cloud.common.util.MD5Util;
import com.cloud.common.vo.LoginUserVO;
import com.cloud.gateway.util.ParamUtils;
import com.cloud.gateway.util.ProcessUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author 60003404
 * @Description TODO
 * @Date 2022/5/8 21:28
 * @Version 1.0
 */
@Log4j2
public class SignVerify {

    public static ImmutablePair<Boolean,String> sign(ServerWebExchange exchange, LoginUserVO userVO, String authToken){
        final ServerHttpRequest request = exchange.getRequest();
        final String bodyData = ParamUtils.readData(exchange);
        String path=request.getPath().toString();

        String timestamp = request.getHeaders().getFirst("timestamp");
        String headerSign = request.getHeaders().getFirst("sign");

        String sign = MD5Util.md5(authToken + "&" + timestamp + "&" + userVO.getSignStr() + "&" + path + "&"+bodyData);

        if (!sign.equals(headerSign)){
           return ImmutablePair.of(false,"签名错误");
        }
        return  ImmutablePair.of(true,null);
    }
}
