package com.cloud.gateway.util;

import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author 60003404
 * @Description TODO
 * @Date 2022/5/8 16:33
 * @Version 1.0
 */
@Log4j2
public class ParamUtils {


    /**
     * 获取参数
     * @param exchange
     * @return
     */
    public static String readData(ServerWebExchange exchange) {

        final ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        long contentLength = headers.getContentLength();

            if (Objects.equals(request.getMethod(), HttpMethod.GET)){
                return readGetData(request);
            }else{
                if (contentLength>0){
                return readBodyData(exchange);
            }
        }
        return "";
    }

    /**
     * get 获取参数
     * @param request
     * @return
     */
    private static String readGetData(ServerHttpRequest request) {
        final String query = request.getURI().getRawQuery();
        return Objects.isNull(query)?"":query;
    }

    /**
     * post获取参数
     * @param exchange
     * @return
     */
    private static String readBodyData(ServerWebExchange exchange) {
        DataBuffer body = exchange.getAttributeOrDefault(ServerWebExchangeUtils.CACHED_REQUEST_BODY_ATTR,new DefaultDataBufferFactory().allocateBuffer().write("{}".getBytes()));
        CharBuffer charBuffer = StandardCharsets.UTF_8.decode(body.asByteBuffer());
        return charBuffer.toString();
    }
}
