package com.imooc.common.result;

import lombok.Data;

import java.util.Map;

/**
 * 自定义响应数据类型枚举升级版本
 *
 * @Title: Result.java
 * @Package com.imooc.common.result
 * @Description: 自定义响应数据结构
 * 				本类可提供给 H5/ios/安卓/公众号/小程序 使用
 * 				前端接受此类数据（json object)后，可自行根据业务去实现相关功能
 *
 * @Copyright: Copyright (c) 2020
 * @Company: www.imooc.com
 * @author 慕课网 - 风间影月
 * @version V2.0
 */
@Data
public class InvokeResult<T> {

    // 响应业务状态码
    private Integer status;

    // 响应消息
    private String msg;

    // 是否成功
    private Boolean success;

    // 响应数据，可以是Object，也可以是List或Map等
    private T data;

    /**
     * 成功返回，带有数据的，直接往OK方法丢data数据即可
     * @param data
     * @return
     */
    public static <R> InvokeResult<R> ok(R data) {
        return new InvokeResult<>(data);
    }
    /**
     * 成功返回，不带有数据的，直接调用ok方法，data无须传入（其实就是null）
     * @return
     */
    public static <K> InvokeResult<K> ok() {
        return new InvokeResult<>(ResponseEnum.SUCCESS);
    }
    public InvokeResult(T data) {
        this.status = ResponseEnum.SUCCESS.status();
        this.msg = ResponseEnum.SUCCESS.msg();
        this.success = ResponseEnum.SUCCESS.success();
        this.data = data;
    }


    /**
     * 错误返回，直接调用error方法即可，当然也可以在ResponseStatusEnum中自定义错误后再返回也都可以
     * @return
     */
    public static <K> InvokeResult<K> error() {
        return new InvokeResult<>(ResponseEnum.FAILED);
    }

    /**
     * 错误返回，map中包含了多条错误信息，可以用于表单验证，把错误统一的全部返回出去
     * @param map
     * @return
     */
    public static InvokeResult<?> errorMap(Map<Object, Object> map) {
        return new InvokeResult<>(ResponseEnum.FAILED, map);
    }

    /**
     * 错误返回，map中包含了多条错误信息，可以用于表单验证，把错误统一的全部返回出去
     */
    public static InvokeResult<?> errorMap(ResponseEnum responseEnum, Map<Object, Object> map) {
        return new InvokeResult<>(responseEnum, map);
    }

    /**
     * 错误返回，直接返回错误的消息
     * @param msg
     * @return
     */
    public static <K> InvokeResult<K> errorMsg(String msg) {
        return new InvokeResult<>(ResponseEnum.FAILED, msg);
    }

    /**
     * 错误返回，token异常，一些通用的可以在这里统一定义
     * @return
     */
    public static <K> InvokeResult<K> errorTicket() {
        return new InvokeResult<>(ResponseEnum.TICKET_INVALID);
    }

    /**
     * 自定义错误范围，需要传入一个自定义的枚举，可以到[ResponseStatusEnum.java[中自定义后再传入
     * @param responseStatus
     * @return
     */
    public static <K> InvokeResult<K> errorCustom(ResponseEnum responseStatus) {
        return new InvokeResult<>(responseStatus);
    }
    public static <K> InvokeResult<K> exception(ResponseEnum responseStatus) {
        return new InvokeResult<>(responseStatus);
    }

    public InvokeResult(ResponseEnum responseStatus) {
        this.status = responseStatus.status();
        this.msg = responseStatus.msg();
        this.success = responseStatus.success();
    }
    public InvokeResult(ResponseEnum responseStatus, T data) {
        this.status = responseStatus.status();
        this.msg = responseStatus.msg();
        this.success = responseStatus.success();
        this.data = data;
    }
    public InvokeResult(ResponseEnum responseStatus, String msg) {
        this.status = responseStatus.status();
        this.msg = msg;
        this.success = responseStatus.success();
    }

    public InvokeResult() {
    }

}
