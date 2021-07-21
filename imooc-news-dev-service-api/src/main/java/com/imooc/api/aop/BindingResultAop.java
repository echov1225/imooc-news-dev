package com.imooc.api.aop;

import com.imooc.common.exception.MyCustomException;
import com.imooc.common.result.InvokeResult;
import com.imooc.common.result.ResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: shiwenwei
 * @date: 2021/7/3 2:04 PM
 * @since v1.0
 */
@Slf4j
@Aspect
@Component
@Order(2)
public class BindingResultAop {

    @Pointcut("execution(public * com.imooc.api.controller.*.*.*(..))")
    public void pointCut(){}

    @Around("pointCut() && args(.., bindingResult)")
    public Object bindingResult(ProceedingJoinPoint pjp, BindingResult bindingResult) throws Throwable {
        // 判断 BindingResult 中是否保存了错误的验证信息，如果有，则返回
        if (bindingResult.hasErrors()) {
            Map<Object, Object> errorMap = new HashMap<>();
            bindingResult.getFieldErrors().forEach(fieldError -> {
                errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            });
            return InvokeResult.errorMap(errorMap);
        }
        return pjp.proceed();
    }

}
