package com.imooc.api.controller.user;

import com.imooc.common.result.InvokeResult;
import com.imooc.model.bo.UpdateUserInfoBO;
import com.imooc.model.vo.UserAccountInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: shiwenwei
 * @date: 2021/8/15 5:02 PM
 * @since v1.0
 */
@Api(value = "用户信息相关 Controller", tags = {"用户信息相关 Controller"})
@RequestMapping("user")
public interface UserControllerApi {

    @ApiOperation(value = "获得用户账户信息", notes = "获得用户账户信息", httpMethod = "POST")
    @PostMapping("getAccountInfo")
    InvokeResult<UserAccountInfoVO> getAccountInfo(@RequestParam String userId);

    @ApiOperation(value = "完善用户账户信息", notes = "完善用户账户信息", httpMethod = "POST")
    @PostMapping("updateUserInfo")
    InvokeResult<?> updateUserInfo(@RequestBody UpdateUserInfoBO updateUserInfoBO);

}
