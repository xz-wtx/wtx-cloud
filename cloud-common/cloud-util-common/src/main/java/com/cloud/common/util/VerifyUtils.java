package com.cloud.common.util;

import com.cloud.common.exception.CommonException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Objects;

/**
 * @author 60003404
 * @Description TODO
 * @Date 2022/4/26 21:23
 * @Version 1.0
 */
@Log4j2
public class VerifyUtils {

    /**
     * 验证是否为空
     * @param t
     * @param <T>
     */
    public static <T> void isEmpty(T t){
        if (Objects.isNull(t)){
            throw new CommonException("数据不存在");
        }
    }

    /**
     * 验证是否为空
     * @param t
     * @param <T>
     * @return
     */
    public static <T> ImmutablePair<Boolean,String> isEmpty(T t,String msg){
        if (Objects.isNull(t)){
            return ImmutablePair.of(true,msg);
        }
        return ImmutablePair.of(false,null);
    }
}
