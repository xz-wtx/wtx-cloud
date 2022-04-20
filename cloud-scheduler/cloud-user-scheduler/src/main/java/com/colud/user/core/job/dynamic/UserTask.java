package com.colud.user.core.job.dynamic;

import com.cloud.user.entity.SysUserEntity;
import com.cloud.user.feign.UserFeign;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author wtx
 * @Description TODO
 * @Date 2022/4/6 9:58
 * @Version 1.0
 */
@Component
@Log4j2
public class UserTask {

    @Autowired
    UserFeign userFeign;

    @XxlJob("syncUserTask")
    public void syncUserTask(){
        log.info("SyncUserTask启动");
        SysUserEntity sysUser=new SysUserEntity(){{
           setAccount(UUID.randomUUID().toString());
        }};
        userFeign.insertUser(sysUser);
        log.info("调用成功");
    }
}
