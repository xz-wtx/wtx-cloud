package com.cloud.common.util;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import com.cloud.common.exception.CommonException;
import lombok.extern.log4j.Log4j2;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author wtx
 * @Description TODO
 * @Date 2022/4/2 19:52
 * @Version 1.0
 */
@Log4j2
public class JwtUtils {

    /**
     * 过期时间设置(120分钟)
     */
    public static final long EXPIRE_TIME = 1000*60*60*2;

    /**
     * 私钥设置(随便乱写的)
     */
    private static final String TOKEN_SECRET = "5xcJVrXNyQDIxK1l2RS9nw";

    //加密算法设置

    public static Algorithm algorithm =Algorithm.HMAC256(TOKEN_SECRET);


    //过期时间
   public static Date getExpireTime(){
       return new Date(System.currentTimeMillis()+EXPIRE_TIME);
   }

    /**
     * 签名生成token
     * @param obj 信息
     * @return
     */
    public static String createJWT(Object obj){
        return createJWT(obj,getExpireTime());
    }
    /**
     * 签名生成token
     * @param obj 信息
     * @return
     */
    public static String createJWT(Object obj,Date date){


        //头部信息
        Map<String,Object> header=new HashMap<>(2);
        header.put("typ","JWT");
        header.put("alg","HS256");

        //
        return JWT.create()
                .withHeader(header)
                //签发人
                .withIssuer("admin")
                //签发时间
                .withIssuedAt(new Date())
                //自定义声明
                .withClaim("claim","xxx")
                //主题：用户信息
                .withSubject(JSONObject.toJSONString(obj))
                //过期时间
                .withExpiresAt(date)
                //编号
                .withJWTId(UUID.randomUUID().toString())
                .sign(algorithm);

    }

    /**
     * 解析token
     * @param token
     * @param t
     * @param <T>
     * @return
     */
    public static <T>T  parseJWT(String token,Class<T> t){

        try {
            final  Verification  require = JWT.require(algorithm);
            final  DecodedJWT  verify = require.build().verify(token);


            final String subject = verify.getSubject();
            final T obj = JSONObject.parseObject(subject, t);

            return obj;
        }catch (Exception e){
            e.printStackTrace();
            if (e instanceof SignatureVerificationException){
                throw new CommonException(402,"token无效");
            }
            if (e instanceof TokenExpiredException){
                throw new CommonException(401,"token过期，请重新登录");
            }
            throw new CommonException(403,"token解析异常");
        }

    }

}
