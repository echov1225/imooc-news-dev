package com.imooc.api.interceptors;

import com.imooc.api.constants.Constants;
import com.imooc.common.exception.MyCustomException;
import com.imooc.common.result.ResponseEnum;
import com.imooc.common.utils.RedisOperator;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: shiwenwei
 * @date: 2021/9/2 10:49 PM
 * @since v1.0
 */
public class UserTokenInterceptor implements HandlerInterceptor {

    @Setter(onMethod_ = @Autowired)
    private RedisOperator redis;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userId = request.getHeader("headerUserId");
        String userToken = request.getHeader("headerUserToken");

        verifyUserIdToken(userId, userToken);

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    private void verifyUserIdToken(String userId, String userToken) {
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(userToken)) {
            throw new MyCustomException(ResponseEnum.UN_LOGIN);
        }
        String redisToken = redis.get(Constants.Redis.USER_TOKEN + userId);
        if (StringUtils.isEmpty(redisToken)) {
            throw new MyCustomException(ResponseEnum.UN_LOGIN);
        }
        if (!redisToken.equals(userToken)) {
            throw new MyCustomException(ResponseEnum.TICKET_INVALID);
        }
    }
}
