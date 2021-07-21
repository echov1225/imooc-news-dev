package com.imooc.api.aop;

import com.imooc.common.result.InvokeResult;
import com.imooc.common.utils.IPUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author: shiwenwei
 * @date: 2021/7/3 1:26 PM
 * @since v1.0
 */
@Slf4j
@Aspect
@Component
@Order(1)
public class WebLogAop {

    @Pointcut("execution(public * com.imooc.api.controller.*.*.*(..))")
    public void webLog(){}

    @Before("webLog()")
    public void before(JoinPoint point) {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            // 记录下请求内容
            if (log.isInfoEnabled()) {
                log.info("URL: -X {} {}", request.getMethod(), request.getRequestURL().toString());
                log.info("Remote Address: {}", IPUtil.getRequestIp(request));
                log.info("Request Payload: {}", Arrays.toString(point.getArgs()));
            }
        }
    }

    @AfterReturning(value = "webLog()", returning = "result")
    public void afterReturning(InvokeResult<?> result) {
        if (log.isInfoEnabled()) {
            log.info("Return msg: {}", result);
        }
    }

}
