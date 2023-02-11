package com.lyu.exception;

/**
 * @author LEE
 * @time 2023/2/4 9:36
 */
public class CommodityBidException  extends GlobalException{

    public CommodityBidException(Integer code, String message) {
        super(code, message);
    }
}
