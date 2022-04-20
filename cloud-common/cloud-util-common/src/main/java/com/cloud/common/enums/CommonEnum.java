package com.cloud.common.enums;

/**
 * @author wtx
 * @Description 所有业务异常的枚举
 * @date 2016年11月12日 下午5:04:51
 */
public enum CommonEnum {

	//公共
	SUCCESS(200,"操作成功"),
	FAILURE(0,"操作失败"),

	ERROR_500(500,"服务内部错误"),
	ERROR_404(404,"Not Found"),
	ERROR_415(415,"Unsupported Media Type"),

	NO_USER(1000,"用户不存在"),
	USER_DISABLE(1001,"用户不可用"),
	;
	private int code;
	private String message;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	private CommonEnum() {
	}

	private CommonEnum(int code) {
		this.code = code;
	}

	private CommonEnum(int code, String msg) {
		this.code = code;
		this.message = msg;
	}



}
