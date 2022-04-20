package com.cloud.gateway.filter;

import com.cloud.gateway.util.ProcessUtil;
import com.cloud.common.constant.BaseConstant;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author wtx
 * @Description TODO 权限验证
 * @Date 2022/4/6 19:44
 * @Version 1.0
 */
@Log4j2
@Configuration
public class AuthFilter implements GlobalFilter, Ordered {

    @Autowired
    RedissonClient redissonClient;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        final ServerHttpResponse response = exchange.getResponse();
        final ServerHttpRequest request = exchange.getRequest();

        //请求是否需要鉴权,不需要就放行
        final RBloomFilter<Object> bloomFilter = redissonClient.getBloomFilter(BaseConstant.BLOOM_FILTER_ALL);
        if (!bloomFilter.contains(request.getURI())) {
            return chain.filter(exchange);
        }

        //需要鉴权
        String authToken = response.getHeaders().getFirst(BaseConstant.AUTH_TOKEN);
        if (StringUtils.isBlank(authToken)) {
            return response.writeWith(Mono.just(ProcessUtil.endProcess(HttpStatus.FORBIDDEN, response, "请求需要授权")));
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
