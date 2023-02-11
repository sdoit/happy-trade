package com.lyu.common;

/**
 * @author LEE
 * @time 2023/2/9 21:00
 */
public class CommonResultPage<T> extends CommonResult<T> {

    /**
     * 记录总数
     */
    private Long total;

    public CommonResultPage(int code, boolean flag, String message, T data, Long total) {
        super(code, flag, message, data);
        this.total = total;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public static <T> CommonResultPage<T> Result(CodeAndMessage codeAndMessage, T data, Long total) {
        return new CommonResultPage<T>(codeAndMessage.getCode(), codeAndMessage.getFlag(), codeAndMessage.getMessage(), data, total);

    }

}
