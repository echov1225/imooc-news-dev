package com.imooc.common.advice;

import com.imooc.common.exception.MyCustomException;
import com.imooc.common.result.InvokeResult;
import com.imooc.common.result.ResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: shiwenwei
 * @date: 2021/8/15 4:25 PM
 * @since v1.0
 */
@Slf4j
@ControllerAdvice
public class MyExceptionHandler {

    @ResponseBody
    @ExceptionHandler(MyCustomException.class)
    public InvokeResult<?> handler(MyCustomException e) {
        if (log.isErrorEnabled()) {
            log.error(e.getMessage(), e);
        }
        return InvokeResult.exception(e.getResponseEnum());
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public InvokeResult<?> handler(Exception e) {
        if (log.isErrorEnabled()) {
            log.error(e.getMessage(), e);
        }
        return InvokeResult.exception(ResponseEnum.SYSTEM_OPERATION_ERROR);
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public InvokeResult<?> handler(MethodArgumentNotValidException e) {
        if (log.isErrorEnabled()) {
            log.error(e.getMessage(), e);
        }

        // 判断 BindingResult 中是否保存了错误的验证信息，如果有，则返回
        BindingResult bindingResult = e.getBindingResult();
        if (bindingResult.hasErrors()) {
            Map<Object, Object> errorMap = new HashMap<>();
            bindingResult.getFieldErrors().forEach(fieldError ->
                    errorMap.put(fieldError.getField(), fieldError.getDefaultMessage()));
            return InvokeResult.errorMap(ResponseEnum.ARGUMENT_INVALID, errorMap);
        }
        return InvokeResult.error();
    }

}
