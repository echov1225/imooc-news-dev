package com.imooc.api.interceptors;

import com.imooc.api.constants.Constants;
import com.imooc.common.enums.UserStatus;
import com.imooc.common.exception.MyCustomException;
import com.imooc.common.result.ResponseEnum;
import com.imooc.common.utils.JsonUtils;
import com.imooc.common.utils.RedisOperator;
import com.imooc.model.pojo.AppUser;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author: shiwenwei
 * @date: 2021/9/2 11:27 PM
 * @since v1.0
 */
public class UserActiveInterceptor implements HandlerInterceptor {

    @Setter(onMethod_ = @Autowired)
    private RedisOperator redis;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userId = request.getHeader("headerUserId");
        String redisUserJson = redis.get(Constants.Redis.REDIS_USER_INFO + userId);
        if (StringUtils.isEmpty(redisUserJson)) {
            throw new MyCustomException(ResponseEnum.UN_LOGIN);
        }
        AppUser user = JsonUtils.jsonToPojo(redisUserJson, AppUser.class);
        if (user == null || !Objects.equals(user.getActiveStatus(), UserStatus.ACTIVE.type)) {
            throw new MyCustomException(ResponseEnum.USER_INACTIVE_ERROR);
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
