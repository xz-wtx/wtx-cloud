package com.cloud.common.response;

import com.cloud.common.enums.CommonEnum;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    public static <T> R<T> genSuccessResultEmptyObject() {
        return genResult(CommonEnum.SUCCESS.getCode(), (T)"{}", CommonEnum.SUCCESS.getMessage());
    }
    public static <T> R<T> genSuccessResultEmptyArray() {
        return genResult(CommonEnum.SUCCESS.getCode(), (T)new ArrayList<>(), CommonEnum.SUCCESS.getMessage());
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

    /**
     * @Valid 参数错误
     * @param result
     * @return
     */
    public static <T> R<T> errorParam(BindingResult result) {

        return errorParam("参数错误",result);
    }
    /**
     * @Valid 参数错误
     * @param errMsg 错误信息的开头
     * @param result
     * @return
     */
    public static <T> R<T> errorParam(String errMsg, BindingResult result) {
        List<FieldError> errors = result.getFieldErrors();
        StringBuilder returnMessage = new StringBuilder();
        returnMessage.append(errMsg).append(":参数：{\n");
        for (FieldError error : errors) {
            returnMessage.append(error.getField());
            returnMessage.append(":");
            returnMessage.append(error.getDefaultMessage());
            returnMessage.append("\n");
        }
        returnMessage.append("}");
        return genErrorResult(returnMessage.toString());
    }


}
