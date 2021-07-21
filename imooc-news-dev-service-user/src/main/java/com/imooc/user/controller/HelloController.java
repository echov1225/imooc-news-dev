package com.imooc.user.controller;

import com.imooc.api.controller.user.HelloControllerApi;
import com.imooc.common.result.InvokeResult;
import com.imooc.common.utils.RedisOperator;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: shiwenwei
 * @date: 2021/5/30 12:57 AM
 * @since v1.0
 */
@RestController
public class HelloController implements HelloControllerApi {

    @Setter(onMethod_ = @Autowired)
    private RedisOperator redis;

    @Override
    public InvokeResult<?> hello() {
        return InvokeResult.ok();
    }

    @Override
    public InvokeResult<?> redis() {
        redis.set("age", "18");
        return InvokeResult.ok(redis.get("age"));
    }
}
