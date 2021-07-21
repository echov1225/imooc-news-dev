package com.imooc.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

/**
 * @author: shiwenwei
 * @date: 2021/5/30 6:25 PM
 * @since v1.0
 */
@Slf4j
@Component
public class SMSUtils {

    private static final String TEMPLATE = "【慕课网】您的验证码为：{0}，该验证码在30分钟内有效，请勿给他人使用～";

    public void sendSMS(String mobile, String code) {
        String  content = MessageFormat.format(TEMPLATE, code);
        log.info("Send {} to {}", content, mobile);
    }

}
