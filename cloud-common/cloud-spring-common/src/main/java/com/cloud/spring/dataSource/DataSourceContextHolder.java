package com.cloud.spring.dataSource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @Description TODO
 * @Date 2021/8/26
 * @Author WTX
 * @Version
 **/
@Slf4j
public class DataSourceContextHolder extends AbstractRoutingDataSource {

    public static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * 获取数据源
     */
    @Override
    protected Object determineCurrentLookupKey() {

        if (CONTEXT_HOLDER.get()==null){
            this.getResolvedDataSources().forEach((k,v)->{
                if (v==this.getResolvedDefaultDataSource()){
                    log.info("当前选择的数据源是:" + k);
                }
            });
        }else{
            log.info("当前选择的数据源是:" + CONTEXT_HOLDER.get());
        }
        return CONTEXT_HOLDER.get();
    }

    /**
     * 设置数据源
     */
    public static void setDataSource(String db) {
        CONTEXT_HOLDER.set(db);
    }

    /**
     * 取得当前数据源
     *
     * @return
     */
    public static String getDataSource() {
        return CONTEXT_HOLDER.get();
    }

    /**
     * 清除上下文数据
     */
    public static void clear() {
        CONTEXT_HOLDER.remove();
    }

}
