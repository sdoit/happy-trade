package com.lyu.exception;

/**
 * @author LEE
 * @time 2023/4/14 15:34
 */
public class ReportException extends GlobalException {
    public ReportException(Integer code, String message) {
        super(code, message);
    }
}