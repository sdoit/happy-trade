package com.lyu.exception;

/**
 * @author LEE
 * @time 2023/2/19 16:59
 */
public class SSEException extends GlobalException{
    public SSEException(Integer code, String message) {
        super(code, message);
    }
}
