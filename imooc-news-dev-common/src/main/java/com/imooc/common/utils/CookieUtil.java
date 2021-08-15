package com.imooc.common.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author: shiwenwei
 * @date: 2021/8/15 4:27 PM
 * @since v1.0
 */
public class CookieUtil {

    public static final Integer COOKIE_DELETE = 0;

    public static void setCookie(HttpServletResponse response,
                                 String cookieName,
                                 String cookieValue,
                                 Integer maxAge,
                                 String domainName) {
        try {
            cookieValue = URLEncoder.encode(cookieValue, "utf-8");
            setCookieValue(response, cookieName, cookieValue, maxAge, domainName);
//            Cookie cookie = new Cookie(cookieName, cookieValue);
//            cookie.setMaxAge(maxAge);
//            cookie.setDomain("imoocnews.com");
//            cookie.setPath("/");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static void setCookieValue(HttpServletResponse response,
                                      String cookieName,
                                      String cookieValue,
                                      Integer maxAge,
                                      String domainName) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setMaxAge(maxAge);
//        cookie.setDomain("imoocnews.com");
        cookie.setDomain(domainName);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static void deleteCookie(HttpServletResponse response,
                                    String cookieName,
                                    String domainName) {
        try {
            String deleteValue = URLEncoder.encode("", "utf-8");
            setCookieValue(response, cookieName, deleteValue, COOKIE_DELETE, domainName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

}
