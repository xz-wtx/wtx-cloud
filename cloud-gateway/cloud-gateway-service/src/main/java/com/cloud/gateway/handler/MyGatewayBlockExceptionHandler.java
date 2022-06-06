package com.cloud.gateway.handler;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.cloud.common.exception.CommonException;
import com.cloud.gateway.util.ProcessUtil;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

/**
 * @author wtx
 * @Description TODO sentinel异常处理 仿SentinelGatewayBlockExceptionHandler
 * @Date 2022/4/9 23:13
 * @Version 1.0
 */
@Log4j2
public class MyGatewayBlockExceptionHandler implements WebExceptionHandler {

    private Mono<Void> writeResponse(ServerResponse response, ServerWebExchange exchange) {
        ServerHttpResponse serverHttpResponse = exchange.getResponse();
        final DataBuffer dataBuffer = ProcessUtil.endProcess(HttpStatus.TOO_MANY_REQUESTS, exchange.getResponse(),  "系统繁忙，稍后重试！");
        return serverHttpResponse.writeWith(Mono.just(dataBuffer));
    }

    @Override
    @NonNull
    public Mono<Void> handle(ServerWebExchange exchange,@NonNull Throwable ex) {
        final ServerHttpRequest request = exchange.getRequest();
        if (exchange.getResponse().isCommitted()) {
            ex.printStackTrace();
            return Mono.error(ex);
        }
        log.error("[全局异常处理] 异常请求路径:{}, 记录异常信息:{}", request.getPath(), ex.getMessage());
        // This exception handler only handles rejection by Sentinel.
        if (!BlockException.isBlockException(ex)) {
            ex.printStackTrace();
            final ServerHttpResponse response = exchange.getResponse();
            String message= ex.getMessage();
            if (ex instanceof final ResponseStatusException ex1){
                if (ex1.getStatus()==HttpStatus.NOT_FOUND){
                    message = ex.getMessage() + request.getPath();
                    return response.writeWith(Mono.just(ProcessUtil.endProcess(ex1.getStatus(),response,message)));
                }
            }

            if (ex instanceof CommonException ex2){
                return response.writeWith(Mono.just(ProcessUtil.endProcess(ex2.getCode(),response,message)));
            }
            return response.writeWith(Mono.just(ProcessUtil.endProcess(HttpStatus.INTERNAL_SERVER_ERROR,response,message)));
        }
        return handleBlockedRequest(exchange, ex)
                .flatMap(response -> writeResponse(response, exchange));
    }

    private Mono<ServerResponse> handleBlockedRequest(ServerWebExchange exchange, Throwable throwable) {
        return GatewayCallbackManager.getBlockHandler().handleRequest(exchange, throwable);
    }

}
