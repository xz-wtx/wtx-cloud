package com.cloud.gateway.util;

import com.alibaba.fastjson.JSONObject;
import com.cloud.common.response.BR;
import com.cloud.common.response.R;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;

import java.nio.charset.StandardCharsets;

/**
 *
 * @author wtx
 */
public class ProcessUtil {

    /****
     * 结束程序
     * @param response
     * @param msg
     */
    public static DataBuffer endProcess(HttpStatus httpStatus,ServerHttpResponse response,String msg){
        response.setStatusCode(httpStatus);
        return  endProcess(response, httpStatus.value(), msg);
    }
    /****
     * 结束程序
     * @param response
     * @param msg
     */
    public static DataBuffer endProcess(Integer code,ServerHttpResponse response,String msg){
        response.setRawStatusCode(code);
        return  endProcess( response, code, msg);
    }
    /****
     * 结束程序
     * @param response
     * @param code
     * @param msg
     */
    public static DataBuffer endProcess(ServerHttpResponse response, Integer code, String msg){
        //指定编码，否则在浏览器中会中文乱码
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        final R<Object> result = BR.genErrorResult(code,msg);
        byte[] bits = JSONObject.toJSONString(result).getBytes(StandardCharsets.UTF_8);
        return response.bufferFactory().wrap(bits);
    }

}
