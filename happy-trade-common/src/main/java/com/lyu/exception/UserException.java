package com.lyu.exception;

/**
 * @author LEE
 * @time 2022/12/26 13:54
 */
public class UserException extends GlobalException {

    public UserException(Integer code, String message) {
        super(code, message);
    }
}
