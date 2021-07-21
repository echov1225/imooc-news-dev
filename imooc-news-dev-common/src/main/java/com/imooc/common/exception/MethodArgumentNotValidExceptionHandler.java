package com.imooc.common.exception;

import com.imooc.common.result.InvokeResult;
import com.imooc.common.result.ResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: shiwenwei
 * @date: 2021/7/3 3:17 PM
 * @since v1.0
 */
@Slf4j
@ControllerAdvice
public class MethodArgumentNotValidExceptionHandler {

    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
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
