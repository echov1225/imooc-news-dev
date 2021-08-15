package com.imooc.api.constants;

/**
 * @author: shiwenwei
 * @date: 2021/6/2 12:30 AM
 * @since v1.0
 */
public interface Constants {

    interface Redis {

        String MOBILE_SMS_CODE = "sms_code:";

        String USER_TOKEN = "user_token:";

    }

    interface Cookie {
        Integer COOKIE_MONTH = 30 * 24 * 60 * 60;
    }

}
