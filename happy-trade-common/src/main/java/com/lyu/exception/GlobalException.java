package com.lyu.exception;

/**
 * @author LEE
 * @time 2023/1/1 11:35
 */
public class GlobalException extends RuntimeException{
    private final Integer code;
    private final String message;

    public GlobalException(Integer code,String message) {
        this.message = message;

        this.code = code;
    }


    @Override
    public String getMessage() {
        return message;
    }
    public Integer getCode() {
        return code;
    }
}
