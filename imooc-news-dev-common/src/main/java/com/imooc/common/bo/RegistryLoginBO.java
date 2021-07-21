package com.imooc.common.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author: shiwenwei
 * @date: 2021/7/2 11:43 PM
 * @since v1.0
 */
@Data
public class RegistryLoginBO {

    @NotBlank(message = "手机号不能为空")
    private String mobile;

    @NotBlank(message = "验证码不能为空")
    private String smsCode;

}
