package com.cloud.gateway.routes;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.event.EnableBodyCachingEvent;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.AdaptCachedBodyGlobalFilter;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * @author wtx
 * @Description TODO nacos动态路由，提供增删改
 *
 * TODO [
 *     {
 *         "filters": [
 *             {
 *                 "args": {
 *                     "parts": "1" //1不带前缀、0带前缀
 *                 },
 *                 "name": "StripPrefix"
 *             }
 *         ],
 *         "id": "user",
 *         "order": -1,
 *         "predicates": [
 *             {
 *                 "args": {
 *                     "pattern": "/user/**"
 *                 },
 *                 "name": "Path"
 *             }
 *         ],
 *         "uri": "lb://user"
 *     }
 * ]
 *
 * @Date 2022/4/6 19:54
 * @Version 1.0
 */
@Log4j2
@Component
public class DynamicRoute implements ApplicationEventPublisherAware {
    @Autowired
    private AdaptCachedBodyGlobalFilter adaptCachedBodyGlobalFilter;
    @Autowired
    private RouteDefinitionWriter routedefinitionWriter;

    private ApplicationEventPublisher publisher;

    private String dataId="gateway-router.json";

    @Value("${spring.cloud.nacos.config.group}")
    private String group;
    @Value("${spring.cloud.nacos.config.server-addr}")
    private String serverAddr;
    @Value("${spring.cloud.nacos.config.namespace}")
    private String namespace;
    @Value("${spring.cloud.nacos.password}")
    private String password;
    @Value("${spring.cloud.nacos.username}")
    private String username;

    private long timeout=5000;

    private static final List<String> ROUTE_LIST = new ArrayList<>();

    @PostConstruct
    public void dynamicRouteByNacosListener() {
        try {
            Properties prop = new Properties();
            prop.put("serverAddr", serverAddr);
            prop.put("namespace", namespace);
            prop.put("password", password);
            prop.put("username", username);
            ConfigService config = NacosFactory.createConfigService(prop);
            String content = config.getConfig(dataId, group, timeout);
            publisher(content);
            config.addListener(dataId, group, new Listener() {
                @Override
                public void receiveConfigInfo(String config) {
                    publisher(config);
                }

                @Override
                public Executor getExecutor() {
                    return null;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 增加路由
     *
     * @param def
     */
    public void addRoute(RouteDefinition def) {
        try {
            routedefinitionWriter.save(Mono.just(def)).subscribe();
            ROUTE_LIST.add(def.getId());
            EnableBodyCachingEvent enableBodyCachingEvent = new EnableBodyCachingEvent(def, def.getId());
            adaptCachedBodyGlobalFilter.onApplicationEvent(enableBodyCachingEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除路由
     *
     */
    public void clearRoute() {
        for (String id : ROUTE_LIST) {
            routedefinitionWriter.delete(Mono.just(id)).subscribe();
        }
        ROUTE_LIST.clear();
    }

    /**
     * 发布路由
     */
    private void publisher(String config) {
        log.debug("当前最新路由=》{}",config);
        clearRoute();
        try {
            log.info("Start updating dynamic routing ....");
            List<RouteDefinition> routeDefinitionList = JSONObject.parseArray(config, RouteDefinition.class);
            for (RouteDefinition route : routeDefinitionList) {
                log.info(route.toString());
                addRoute(route);
            }
            publisher.publishEvent(new RefreshRoutesEvent(this.routedefinitionWriter));
            log.info("update completed ");
        } catch (Exception e) {
            log.error("Failed to update routing information", e);
            e.printStackTrace();
        }

    }

    @Override
    public void setApplicationEventPublisher(@NonNull ApplicationEventPublisher applicationEventPublisher) {
        publisher = applicationEventPublisher;
    }

}
