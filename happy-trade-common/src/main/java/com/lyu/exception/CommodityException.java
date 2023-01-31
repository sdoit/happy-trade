package com.lyu.exception;

/**
 * @author LEE
 * @time 2022/12/29 17:18
 */
public class CommodityException extends GlobalException {

    public CommodityException(Integer code, String message) {
        super(code, message);
    }
}
