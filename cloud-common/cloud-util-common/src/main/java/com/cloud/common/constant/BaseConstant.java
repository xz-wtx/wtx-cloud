package com.cloud.common.constant;

import lombok.extern.log4j.Log4j2;

/**
 * @author wtx
 * @Description TODO
 * @Date 2022/4/7 13:58
 * @Version 1.0
 */
@Log4j2
public class BaseConstant {

    public static final String AUTH_TOKEN="auth_token";

    /**
     * 布隆过滤器,所有请求
     */
    public static final String BLOOM_FILTER_ALL="bloom_filter:all:";
    /**
     * 布隆过滤器,需要鉴权请求
     */
    public static final String BLOOM_FILTER_AUTH="bloom_filter:auth";
}
