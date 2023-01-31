package com.lyu.exception;

/**
 * @author LEE
 * @time 2022/12/30 11:58
 */
public class OrderException extends GlobalException {


    public OrderException(Integer code, String message) {
        super(code, message);
    }
}
