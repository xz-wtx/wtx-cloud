package com.cloud.wechat.config.dataSource;

/**
 * @Description TODO 数据源集合
 * @Date 2021/8/27
 * @Author WTX
 * @Version
 **/
public enum DataSourceEnums {

    wechat("wechat",0),
    ;

    /**
     * 数据源名称
     */
    private final String value;

    /**
     * 0:系统加载,1手动加载
     */
    private final int flag;

    DataSourceEnums(String value,int flag) {
        this.value = value;
        this.flag = flag;
    }

    public String getValue() {
        return value;
    }
    public int getFlag() {
        return flag;
    }
}
