package com.lyu.exception;

/**
 * @author LEE
 * @time 2023/3/19 16:01
 */
public class RequestException extends GlobalException{
    public RequestException(Integer code, String message) {
        super(code, message);
    }
}
