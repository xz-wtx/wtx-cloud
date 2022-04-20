package com.cloud.wechat.config.dataSource;

import com.cloud.spring.dataSource.DataSourceContextHolder;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @Description TODO 拦截mapper包名，切换数据源
 * @Date 2021/8/26
 * @Author WTX
 * @Version
 **/
@Log4j2
@Component
@Aspect
public class DataSourceAspect {

    /**
     * 根据拦截不同的包名 切换数据源 例如com.cloud.user.mapper.user.*.*(..))
     */
    @Before("execution(* com.cloud.wechat.mapper.*.*(..))")
    public void setDataSourceTask() {
        DataSourceContextHolder.setDataSource(DataSourceEnums.wechat.getValue());
    }



    /**
     * 清除数据源(拦截当前整个mapper)
     * @param point
     */
    @After("execution(* com.cloud.wechat.mapper.*.*(..))")
    public void restoreDataSource(JoinPoint point){
        log.info("Restore DataSource to [" + DataSourceContextHolder.getDataSource()
                + "] in Method [" + point.getSignature() + "]");
        DataSourceContextHolder.clear();
    }

}
