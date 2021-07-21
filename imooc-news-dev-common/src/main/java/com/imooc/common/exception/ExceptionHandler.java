package com.imooc.common.exception;

import com.imooc.common.result.InvokeResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常拦截处理，
 * 可以针对异常的类型进行捕获，返回json信息到前端
 * @author: shiwenwei
 * @date: 2021/7/2 10:57 PM
 * @since v1.0
 */
@Slf4j
@ControllerAdvice
public class ExceptionHandler {

    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(MyCustomException.class)
    public InvokeResult<?> handler(MyCustomException e) {
        if (log.isErrorEnabled()) {
            log.error(e.getMessage(), e);
        }
        return InvokeResult.exception(e.getResponseEnum());
    }
}
