package com.cloud.common.response;

import com.cloud.common.enums.CommonEnum;
import java.io.Serializable;

/**
 * @Description TODO
 * @Date 2021/3/20
 * @Author WTX
 * @Version
 **/
public class BR implements Serializable {

    public static <T> R<T> genResult(int resultCode, T data, String message) {
        R<T> result = R.newInstance();
        result.setStatus(resultCode);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    public static <T> R<T> genResult(CommonEnum statusCode) {
        return genResult(statusCode.getCode(), null, statusCode.getMessage());
    }


    public static <T> R<T> genSuccessResult(T data, String message) {
        return genResult(CommonEnum.SUCCESS.getCode(), data, message);
    }

    public static <T> R<T> genSuccessResult(T data) {
        return genResult(CommonEnum.SUCCESS.getCode(), data, CommonEnum.SUCCESS.getMessage());
    }

    public static <T> R<T> genDefSuccessResult(String message) {
        return genResult(CommonEnum.SUCCESS.getCode(), null, message);
    }

    public static <T> R<T> genSuccessResult() {
        return genResult(CommonEnum.SUCCESS.getCode(), null, CommonEnum.SUCCESS.getMessage());
    }


    public static <T> R<T> genErrorResult(String message) {
        return genResult(CommonEnum.FAILURE.getCode(), null, message);
    }

    public static <T> R<T> genErrorResult(int resultCode, String message) {
        return genResult(resultCode, null, message);
    }

}
