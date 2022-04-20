package com.cloud.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wtx
 * @Description TODO api描述
 * @Date 2022/4/4 15:00
 * @Version 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiDescribe {
    /**
     * 功能描述
     * @return
     */
    String value();


    /**
     * 是否禁用：0可用，1禁用， 2删除(删除之后此条数据不在进行任何更新)
     * @return
     */
    int invalid() default 0;
}
