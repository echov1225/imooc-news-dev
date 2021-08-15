package com.imooc.user.controller;

import com.imooc.api.constants.Constants;
import com.imooc.api.controller.user.PassportControllerApi;
import com.imooc.common.bo.RegistryLoginBO;
import com.imooc.common.enums.UserStatus;
import com.imooc.common.result.InvokeResult;
import com.imooc.common.result.ResponseEnum;
import com.imooc.common.utils.RedisOperator;
import com.imooc.common.utils.SMSUtils;
import com.imooc.model.pojo.AppUser;
import com.imooc.user.service.UserService;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;

/**
 * @author: shiwenwei
 * @date: 2021/5/30 6:44 PM
 * @since v1.0
 */
@RestController
public class PassportController implements PassportControllerApi {

    @Setter(onMethod_ = @Autowired)
    private SMSUtils smsUtils;

    @Setter(onMethod_ = @Autowired)
    private RedisOperator redis;

    @Setter(onMethod_ = @Autowired)
    private UserService userService;

    @Override
    public InvokeResult<?> getSMSCode(String mobile) {
        // 生成随机验证码，并发送
        String code = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
        smsUtils.sendSMS(mobile, code);
        // 存储短信验证码，超时30min
        redis.set(Constants.MOBILE_SMS_CODE + mobile, code, 30 * 60);
        return InvokeResult.ok();
    }

    @Override
    public InvokeResult<?> doLogin(@Valid RegistryLoginBO registryLoginBO) {
        // 判断验证码是否合法
        String redisSmsCode = redis.get(Constants.MOBILE_SMS_CODE + registryLoginBO.getMobile());
        if (StringUtils.isEmpty(redisSmsCode)
                || !redisSmsCode.equals(registryLoginBO.getSmsCode())) {
            return InvokeResult.exception(ResponseEnum.SMS_CODE_ERROR);
        }

        // 判断用户是否注册
        AppUser appUser = userService.queryMobileIsExist(registryLoginBO.getMobile());
        if (appUser == null) {
            appUser = userService.createUser(registryLoginBO.getMobile());
        } else {
            if (Objects.equals(appUser.getActiveStatus(), UserStatus.FROZEN.type)) {
                return InvokeResult.exception(ResponseEnum.USER_FROZEN);
            }
        }

        return InvokeResult.ok(appUser);
    }
}
