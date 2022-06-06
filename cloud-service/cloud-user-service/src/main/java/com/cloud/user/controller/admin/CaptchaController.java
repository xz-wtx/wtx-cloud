package com.cloud.user.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.utils.CaptchaUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author 60003404
 * @Description TODO
 * @Date 2022/5/10 20:29
 * @Version 1.0
 */
@RestController
@RequestMapping("/")
@Log4j2
public class CaptchaController extends BaseController{

    @Autowired
    StringRedisTemplate redisTemplate;


    @RequestMapping("/captcha")
    public String code(HttpServletResponse response){
        // 设置请求头为输出图片类型
        response.setContentType("image/jpeg");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setDateHeader("Expires", 0);

        // 算术类型
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(100, 40);
        // 几位数运算，默认是两位
        captcha.setLen(2);
        // 获取运算的公式：3+2=?
        captcha.getArithmeticString();
        String text = captcha.text();
        String key = UUID.randomUUID().toString();
        HashMap<String,String> map = new HashMap<>(2);
        map.put("key", key);
        map.put("image", captcha.toBase64());
        //保存验证码信息，1分钟有效期
        redisTemplate.opsForValue().set("image_code:"+key,text,60L, TimeUnit.SECONDS);
      return JSONObject.toJSONString(map);
    }
}
