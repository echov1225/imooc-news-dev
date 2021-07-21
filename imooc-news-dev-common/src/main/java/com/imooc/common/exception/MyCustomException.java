package com.imooc.common.exception;

import com.imooc.common.result.ResponseEnum;

/**
 * @author: shiwenwei
 * @date: 2021/7/2 10:44 PM
 * @since v1.0
 */
public class MyCustomException extends RuntimeException {

    private ResponseEnum responseEnum;

    public MyCustomException(ResponseEnum responseEnum) {
        super("异常状态码: " + responseEnum.status()
                + ", 异常信息: " + responseEnum.msg());
        this.responseEnum = responseEnum;
    }

    public ResponseEnum getResponseEnum() {
        return responseEnum;
    }

    public void setResponseEnum(ResponseEnum responseEnum) {
        this.responseEnum = responseEnum;
    }
}
