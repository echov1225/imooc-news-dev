package com.imooc.api.controller.user;

import com.imooc.common.result.InvokeResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author: shiwenwei
 * @date: 2021/5/30 12:49 AM
 * @since v1.0
 */
@Api(value = "Controller 的标题", tags = {"hello 调试接口"})
public interface HelloControllerApi {

    @ApiOperation(value = "hello 方法接口", notes = "hello 方法接口", httpMethod = "GET")
    @GetMapping("hello")
    InvokeResult<?> hello();

    @ApiOperation(value = "redis 方法接口", notes = "redis 方法接口", httpMethod = "GET")
    @GetMapping("redis")
    InvokeResult<?> redis();

}
