package com.cloud.config.nacos;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import javax.annotation.PostConstruct;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * @author wtx
 * @Description TODO
 * @Date 2022/4/17 21:33
 * @Version 1.0
 */
@Log4j2
public abstract class NacosConfig{

    private String dataId="";

    @Value("${spring.cloud.nacos.config.group:}")
    private String group;
    @Value("${spring.cloud.nacos.config.server-addr:}")
    private String serverAddr;
    @Value("${spring.cloud.nacos.config.namespace:}")
    private String namespace;
    @Value("${spring.cloud.nacos.password:}")
    private String password;
    @Value("${spring.cloud.nacos.username:}")
    private String username;

    private long timeout=5000;



    @PostConstruct
    public void nacosListener() {
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




    public NacosConfig(String dataId){
        this.dataId=dataId;
    }

    public abstract void publisher(String content);

}
