package com.imooc.user.controller;

import com.imooc.api.controller.user.UserControllerApi;
import com.imooc.common.result.InvokeResult;
import com.imooc.model.bo.UpdateUserInfoBO;
import com.imooc.model.pojo.AppUser;
import com.imooc.model.vo.AppUserVO;
import com.imooc.model.vo.UserAccountInfoVO;
import com.imooc.user.service.UserService;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author: shiwenwei
 * @date: 2021/8/15 6:16 PM
 * @since v1.0
 */
@RestController
public class UserController implements UserControllerApi {

    @Setter(onMethod_ = @Autowired)
    private UserService userService;

    @Override
    public InvokeResult<AppUserVO> getUserInfo(String userId) {
        // 根据userId查询用户信息
        AppUser user = userService.getUser(userId);

        // 返回用户信息
        AppUserVO appUserVO = new AppUserVO();
        BeanUtils.copyProperties(user, appUserVO);

        return InvokeResult.ok(appUserVO);
    }

    @Override
    public InvokeResult<UserAccountInfoVO> getAccountInfo(String userId) {
        // 根据userId查询用户信息
        AppUser user = userService.getUser(userId);

        // 返回用户信息
        UserAccountInfoVO accountInfoVO = new UserAccountInfoVO();
        BeanUtils.copyProperties(user, accountInfoVO);

        return InvokeResult.ok(accountInfoVO);
    }

    @Override
    public InvokeResult<?> updateUserInfo(@Valid UpdateUserInfoBO updateUserInfoBO) {
        userService.updateUserInfo(updateUserInfoBO);
        return InvokeResult.ok();
    }
}
