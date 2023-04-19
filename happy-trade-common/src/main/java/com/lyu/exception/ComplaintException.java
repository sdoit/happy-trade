package com.lyu.exception;

/**
 * @author LEE
 * @time 2023/4/14 15:44
 */
public class ComplaintException extends GlobalException{

    public ComplaintException(Integer code, String message) {
        super(code, message);
    }
}
