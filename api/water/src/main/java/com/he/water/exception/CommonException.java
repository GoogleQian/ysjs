package com.he.water.exception;

/**
 * 公共异常
 * 
 * @author hzh
 *
 */
public class CommonException extends Exception {

	private static final long serialVersionUID = 1L;

	private String respCode;
	private String respMsg;

	public CommonException(String respCode, String respMsg, Exception e) {
		super(respMsg, e);

		this.respCode = respCode;
		this.respMsg = respMsg;
	}

	public CommonException(String respCode, String respMsg) {
		super(respMsg);
		
		this.respCode = respCode;
		this.respMsg = respMsg;
	}

	public CommonException(String respCode) {
		this.respCode = respCode;
	}

	public String getRespCode() {
		return respCode;
	}

	public String getRespMsg() {
		return respMsg;
	}

	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}

}