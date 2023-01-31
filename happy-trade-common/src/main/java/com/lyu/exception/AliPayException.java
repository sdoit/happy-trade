package com.lyu.exception;

/**
 * @author LEE
 * @time 2023/1/13 18:54
 */
public class AliPayException extends GlobalException{

    public AliPayException(Integer code, String message) {
        super(code, message);
    }
}
