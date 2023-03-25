package com.lyu.exception;

/**
 * @author LEE
 * @time 2023/3/19 11:20
 */
public class SmsException extends GlobalException {
    public SmsException(Integer code, String message) {
        super(code, message);
    }
}
