package com.lyu.exception;

/**
 * @author LEE
 * @time 2023/2/11 15:34
 */
public class UserFavoriteException extends GlobalException {
    public UserFavoriteException(Integer code, String message) {
        super(code, message);
    }
}
