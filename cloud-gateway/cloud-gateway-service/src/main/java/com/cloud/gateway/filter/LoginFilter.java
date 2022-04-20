package com.cloud.gateway.filter;

import com.cloud.gateway.util.IPUtils;
import com.cloud.gateway.util.ProcessUtil;
import com.cloud.common.constant.BaseConstant;
import lombok.extern.log4j.Log4j2;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;

/**
 * @author wtx
 * @Description TODO 白名单,通过布隆过滤器检查是否请求是否未本系统的请求
 * @Date 2022/4/6 20:37
 * @Version 1.0
 */
@Log4j2
@Configuration
@RefreshScope
public class LoginFilter implements GlobalFilter, Ordered {

    @Autowired
    RedissonClient redissonClient;

    public String[] whiteIp;
    @Value("${white.ip}")
    public void setWhiteIp(String whiteIp){
        this.whiteIp=whiteIp.split(",");
    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        final ServerHttpResponse response = exchange.getResponse();
        final ServerHttpRequest request = exchange.getRequest();

        if (!"localhost".equals(request.getURI().getHost())){
            //1.检查是否在白名单
            final String ip = IPUtils.getIp(request);
            final int search = Arrays.binarySearch(whiteIp, ip);
            if (search<0){
                return response.writeWith(Mono.just(ProcessUtil.endProcess(HttpStatus.FORBIDDEN,response,"无权限调用")));
            }
         }
            //2.通过布隆过滤器 检查是否请求是否未本系统的请求
        final RBloomFilter<Object> bloomFilter = redissonClient.getBloomFilter(BaseConstant.BLOOM_FILTER_ALL);
        if (!bloomFilter.contains(request.getURI())){
                return response.writeWith(Mono.just(ProcessUtil.endProcess(HttpStatus.FORBIDDEN,response,"找不到服务")));
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
