package com.cloud.common.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.log4j.Log4j2;

import java.util.Base64;

/**
 * @author 60003404
 * @Description TODO 加解密
 * @Date 2022/4/22 21:41
 * @Version 1.0
 */
@Log4j2
public class EncryptDecodeUtils {


    /**
     * base64加密
     * @param obj
     * @param <T>
     * @return
     */
    public static  <T> String  base64Enc(T obj){
        return Base64.getEncoder().encodeToString(JSON.toJSONString(obj).getBytes());
    }
    /**
     * base64解码
     * @param text
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> T  base64Dec(String text,Class<T> obj){
        return JSON.parseObject(new String(Base64.getDecoder().decode(text.getBytes())),obj);
    }

}
