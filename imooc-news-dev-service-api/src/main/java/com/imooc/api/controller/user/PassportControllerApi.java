package com.imooc.api.controller.user;

import com.imooc.model.bo.RegistryLoginBO;
import com.imooc.common.result.InvokeResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @author: shiwenwei
 * @date: 2021/5/30 6:41 PM
 * @since v1.0
 */
@Api(value = "用户注册登录", tags = {"用户注册功能"})
@RequestMapping("passport")
public interface PassportControllerApi {

    @ApiOperation(value = "获取短信验证码", notes = "获取短信验证码", httpMethod = "GET")
    @GetMapping("getSMSCode")
    InvokeResult<?> getSMSCode(@RequestParam String mobile);

    @ApiOperation(value = "一键注册登录", notes = "一键注册登录", httpMethod = "POST")
    @PostMapping("doLogin")
    InvokeResult<Integer> doLogin(@RequestBody RegistryLoginBO registryLoginBO, HttpServletResponse response);

}
