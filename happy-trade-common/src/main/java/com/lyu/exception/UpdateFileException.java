package com.lyu.exception;

/**
 * @author LEE
 * @time 2023/1/29 14:18
 */
public class UpdateFileException extends GlobalException{

    public UpdateFileException(Integer code, String message) {
        super(code, message);
    }
}
