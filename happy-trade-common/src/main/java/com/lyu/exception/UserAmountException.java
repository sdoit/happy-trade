package com.lyu.exception;

/**
 * @author LEE
 * @time 2023/2/3 16:17
 */
public class UserAmountException  extends GlobalException{
    public UserAmountException(Integer code, String message) {
        super(code, message);
    }
}
