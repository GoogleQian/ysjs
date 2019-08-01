package com.he.water.exception;

public class RefundException extends CommonException {
    private static final long serialVersionUID = 1L;

    public RefundException(String respCode, String reqMsg) {
        super(respCode, reqMsg);
    }

}
