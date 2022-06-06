package com.cloud.common.util;

import org.springframework.util.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/** 
 * 提供一系列获取和校验MD5的方法 
 * 
 */  
public class MD5Util {  
  

    /**
     * 使用md5的算法进行加密
     */
    public static String md5(String plainText) {
        return DigestUtils.md5DigestAsHex(plainText.getBytes());
    }
}  