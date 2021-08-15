package com.imooc.user.service;

import com.imooc.model.pojo.AppUser;

/**
 * @author: shiwenwei
 * @date: 2021/8/15 2:54 PM
 * @since v1.0
 */
public interface UserService {

    /**
     * 判断用户是否存在，如果存在返回user信息
     */
    AppUser queryMobileIsExist(String mobile);

    /**
     * 创建用户，新增用户记录到数据库
     */
    AppUser createUser(String mobile);

}