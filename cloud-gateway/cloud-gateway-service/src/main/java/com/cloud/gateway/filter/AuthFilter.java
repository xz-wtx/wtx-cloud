package com.cloud.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.cloud.common.constant.BaseConstant;
import com.cloud.common.constant.RedisConstant;
import com.cloud.common.util.EncryptDecodeUtils;
import com.cloud.common.util.JwtUtils;
import com.cloud.common.util.PathMatcherUtils;
import com.cloud.common.vo.LoginUserVO;
import com.cloud.gateway.filter.verify.SignVerify;
import com.cloud.gateway.util.ProcessUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

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
    StringRedisTemplate redisTemplate;

    /**
     * 历史放行路径
     */
    HashMap<String, String> historyPath=new HashMap<>(50);
    Long currentTime=0L;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        final ServerHttpResponse response = exchange.getResponse();
        final ServerHttpRequest request = exchange.getRequest();
        Long time=System.currentTimeMillis();

        String path=request.getPath().toString();

        if (path.contains("/login")||path.contains("/captcha")){
            return chain.filter(exchange);
        }


        //5分钟后重置历史放行路径
        if (currentTime<time){
            historyPath.clear();
            currentTime=time+1000*60*5;
        }
        //请求是否需要鉴权,不需要就放行
        if (!historyPath.containsKey(path)) {
            String whitePath = redisTemplate.opsForValue().get(RedisConstant.WHITE_PATH);
            if (Objects.nonNull(whitePath)){
                List<String> arrayList = JSONArray.parseArray(RedisConstant.WHITE_PATH, String.class);
                //请求是否需要鉴权,不需要就放行
                for (String wp :arrayList) {
                    if (PathMatcherUtils.pathMatcher.match(wp,path)) {
                        log.info("放行请求：{}",path);
                        historyPath.put(path,"");
                        return chain.filter(exchange);
                    }
                }
            }
        }




        //需要鉴权
        String authToken = request.getHeaders().getFirst(BaseConstant.AUTH_TOKEN);
        if (StringUtils.isBlank(authToken)) {
            return response.writeWith(Mono.just(ProcessUtil.endProcess(HttpStatus.FORBIDDEN, response, "请求需要授权")));
        }

        //解析token
        final LoginUserVO userVO = JwtUtils.parseJWT(authToken, LoginUserVO.class);

        //验证签名
        final ImmutablePair<Boolean, String> pair = SignVerify.sign(exchange, userVO, authToken);
        if (!pair.getLeft()){
            return response.writeWith(Mono.just(ProcessUtil.endProcess(HttpStatus.NOT_ACCEPTABLE, response, pair.getRight())));
        }

        //base64加密用户信息，并写入header
        final ServerHttpRequest newRequest = request.mutate().header(BaseConstant.AUTH_TOKEN_USER, EncryptDecodeUtils.base64Enc(userVO)).build();
        return chain.filter(exchange.mutate().request(newRequest).build());
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
