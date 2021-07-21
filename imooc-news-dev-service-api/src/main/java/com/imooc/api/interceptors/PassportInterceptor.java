package com.imooc.api.interceptors;

import com.imooc.api.constants.Constants;
import com.imooc.common.exception.MyCustomException;
import com.imooc.common.result.ResponseEnum;
import com.imooc.common.utils.IPUtil;
import com.imooc.common.utils.RedisOperator;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: shiwenwei
 * @date: 2021/6/9 11:21 PM
 * @since v1.0
 */
public class PassportInterceptor implements HandlerInterceptor {

    @Setter(onMethod_ = @Autowired)
    private RedisOperator redis;

    /**
     * 拦截请求，访问controller之前
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取用户IP
        String ip = IPUtil.getRequestIp(request);
        if (redis.keyIsExist(Constants.MOBILE_SMS_CODE + ip)) {
            throw new MyCustomException(ResponseEnum.SMS_NEED_WAIT_ERROR);
        }
        // 根据用户ip限制用户60s内只能获得一次验证码
        redis.setnx60s(Constants.MOBILE_SMS_CODE + ip, ip);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    /**
     * 访问controller之后，渲染视图之前
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * 访问controller，渲染视图之后
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
