package com.imooc.user.service.impl;

import com.imooc.api.constants.Constants;
import com.imooc.common.enums.Sex;
import com.imooc.common.enums.UserStatus;
import com.imooc.common.exception.MyCustomException;
import com.imooc.common.result.InvokeResult;
import com.imooc.common.result.ResponseEnum;
import com.imooc.common.utils.DateUtil;
import com.imooc.common.utils.DesensitizationUtil;
import com.imooc.common.utils.JsonUtils;
import com.imooc.common.utils.RedisOperator;
import com.imooc.model.bo.UpdateUserInfoBO;
import com.imooc.model.pojo.AppUser;
import com.imooc.user.mapper.AppUserMapper;
import com.imooc.user.service.UserService;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

/**
 * @author: shiwenwei
 * @date: 2021/8/15 2:56 PM
 * @since v1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Setter(onMethod_ = @Autowired)
    public AppUserMapper appUserMapper;

    @Setter(onMethod_ = @Autowired)
    private Sid sid;

    @Setter(onMethod_ = @Autowired)
    private RedisOperator redis;

    private static final String USER_FACE0 = "http://122.152.205.72:88/group1/M00/00/05/CpoxxFw_8_qAIlFXAAAcIhVPdSg994.png";
    private static final String USER_FACE1 = "http://122.152.205.72:88/group1/M00/00/05/CpoxxF6ZUySASMbOAABBAXhjY0Y649.png";
    private static final String USER_FACE2 = "http://122.152.205.72:88/group1/M00/00/05/CpoxxF6ZUx6ANoEMAABTntpyjOo395.png";

    @Override
    public AppUser queryMobileIsExist(String mobile) {
        Example userExample = new Example(AppUser.class);
        Example.Criteria userCriteria = userExample.createCriteria();
        userCriteria.andEqualTo("mobile", mobile);
        return appUserMapper.selectOneByExample(userExample);
    }

    @Override
    public AppUser createUser(String mobile) {
        AppUser user = new AppUser();

        /*
         * 互联网项目都要考虑可扩展性
         * 如果未来的业务激增，那么就需要分库分表
         * 那么数据库表主键id必须保证全局（全库）唯一，不得重复
         */
        user.setId(sid.nextShort());
        user.setMobile(mobile);
        user.setNickname("用户: " + DesensitizationUtil.commonDisplay(mobile));
        user.setFace(USER_FACE0);

        user.setBirthday(DateUtil.stringToDate("1900-01-01"));
        user.setSex(Sex.secret.type);
        user.setActiveStatus(UserStatus.INACTIVE.type);

        user.setTotalIncome(0);
        user.setCreatedTime(new Date());
        user.setUpdatedTime(new Date());

        appUserMapper.insert(user);
        return user;
    }

    @Override
    public AppUser getUser(String userId) {
        // 查询判断redis中是否包含用户信息，如果包含，则查询后直接返回，就不去查询数据库了
        String userInfo = redis.get(Constants.Redis.REDIS_USER_INFO + userId);
        if (StringUtils.isNoneBlank(userInfo)) {
            return JsonUtils.jsonToPojo(userInfo, AppUser.class);
        }

        // 由于用户信息不怎么会变动，对于一些千万级别的网站来说，这类信息不会直接去查询数据库
        // 那么完全可以依靠redis，直接把查询后的数据存入到redis中
        AppUser user = appUserMapper.selectByPrimaryKey(userId);
        redis.set(Constants.Redis.REDIS_USER_INFO + userId, JsonUtils.objectToJson(user));
        return user;
    }

    @Override
    public void updateUserInfo(UpdateUserInfoBO updateUserInfoBO) {
        AppUser userInfo = new AppUser();
        BeanUtils.copyProperties(updateUserInfoBO, userInfo);
        userInfo.setActiveStatus(UserStatus.ACTIVE.type);
        int result = appUserMapper.updateByPrimaryKeySelective(userInfo);

        if(result != 1) {
            throw new MyCustomException(ResponseEnum.USER_UPDATE_ERROR);
        }

        String userId = userInfo.getId();
        AppUser user = appUserMapper.selectByPrimaryKey(userId);
        redis.set(Constants.Redis.REDIS_USER_INFO + userId, JsonUtils.objectToJson(user));
    }
}
