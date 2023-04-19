package com.lyu.exception;

/**
 * @author LEE
 * @time 2023/4/10 20:41
 */
public class ViolationException extends GlobalException{
    public ViolationException(Integer code, String message) {
        super(code, message);
    }
}
