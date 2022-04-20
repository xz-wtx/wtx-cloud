package com.cloud.spring.exception;


import com.cloud.common.enums.CommonEnum;

/**
 * @author wtx
 */
public class CommonException extends RuntimeException{
	
	private int code;
	
	private String  errMsg;

	public CommonException(int code, String errMsg){
		super();
		this.code = code;
		this.errMsg = errMsg;
	}
	public CommonException(String errMsg){
		super();
		this.code = 500;
		this.errMsg = errMsg;
	}
	public CommonException(CommonEnum commonEnum){
		super();
		this.code = commonEnum.getCode();
		this.errMsg = commonEnum.getMessage();
	}
	public int getCode() {
		return code;
	}
	
	public String getErrorMsg()
	{
		return errMsg;
	}

}
