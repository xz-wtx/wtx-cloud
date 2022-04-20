package com.cloud.common.util;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.http.HttpStatus;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * @author wtx
 * @Description TODO
 * @Date 2022/4/4 20:30
 * @Version 1.0
 */
@Log4j2
public class HttpUtils {


    /**
     * GET 请求
     * @param url
     * @return
     */
    public static ImmutablePair<Boolean,String> get(String url){
        return get(url,60*5L, "222","#33");
    }
    /**
     * GET 请求
     * @param url 请求url
     * @param timeout 超时时间
     * @param headers 请求头
     * @return
     */
    public static ImmutablePair<Boolean,String> get(String url,Long timeout,String... headers){
        HttpResponse<String> response ;
        try {
            HttpRequest.Builder builder = HttpRequest.newBuilder();
            if ((headers.length&1)==0){
                builder.headers(headers);
            }
            HttpRequest build = builder.GET()
                    .timeout(Duration.ofSeconds(timeout))
                    .uri(new URI(url))
                    .build();


            response= HttpClient.newHttpClient()
                    .send(build, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("请求异常",e);
        }
        assert response != null;
        int statusCode = response.statusCode();
        return ImmutablePair.of(HttpStatus.SC_OK==statusCode,response.body());
    }


    /**
     * POST 请求
     * @param url
     * @param body
     * @return
     */
    public static ImmutablePair<Boolean,String> post(String url,String body){
        return post(url,body,60*5L, (String) null);
    }
    /**
     * POST 请求
     * @param url url
     * @param body json数据
     * @param timeout 超时时间
     * @param headers 请求头
     * @return
     */
    public static  ImmutablePair<Boolean,String> post(String url,String body,Long timeout,String... headers){
        HttpResponse<String> response ;
        try {
            HttpRequest.Builder builder = HttpRequest.newBuilder();
                    if ((headers.length&1)==0){
                        builder.headers(headers);
                    }
            HttpRequest build = builder.POST(HttpRequest.BodyPublishers.ofString(body))
                    .timeout(Duration.ofSeconds(timeout))
                    .header("Content-Type", "application/json")
                    .uri(new URI(url))
                    .build();
            response= HttpClient.newHttpClient()
                    .send(build, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("请求异常",e);
        }
        assert response != null;
        int statusCode = response.statusCode();
        return ImmutablePair.of(HttpStatus.SC_OK==statusCode,response.body());
    }
}
