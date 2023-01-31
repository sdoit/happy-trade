package com.lyu.common;

/**
 * @author LEE
 * @time 2022/12/26 11:27
 */
public class CommonResult<T> {
    /**
     * 返回代码
     */
    private int code;
    /**
     * 是否成功
     */
    private boolean flag;
    /**
     * 返回信息
     */
    private String message;
    /**
     * 携带数据
     */
    private T data;

    public CommonResult(int code, boolean flag, String message, T data) {
        this.code = code;
        this.flag = flag;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> CommonResult<T> createCommonResult(CodeAndMessage codeAndMessage, T data) {
        return new CommonResult<T>(codeAndMessage.getCode(), codeAndMessage.getFlag(), codeAndMessage.getMessage(), data);

    }
}
