package com.imooc.model.vo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author: shiwenwei
 * @date: 2021/8/15 10:15 PM
 * @since v1.0
 */
@Data
public class UserAccountInfoVO {

    private String id;

    private String mobile;

    private String nickname;

    private String face;

    private String realname;

    private String email;

    private Integer sex;

    private Date birthday;

    private String province;

    private String city;

    private String district;

}
