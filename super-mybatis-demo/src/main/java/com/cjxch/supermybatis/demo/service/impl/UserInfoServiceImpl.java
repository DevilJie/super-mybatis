package com.cjxch.supermybatis.demo.service.impl;

import com.cjxch.supermybatis.core.Service.impl.BaseServiceImpl;
import com.cjxch.supermybatis.core.datasource.ChooseDataSource;
import com.cjxch.supermybatis.demo.entity.User;
import com.cjxch.supermybatis.demo.service.UserInfoService;
import com.cjxch.supermybatis.demo.dao.UserInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/21/20:37
 */
@Service
@ChooseDataSource("master")
public class UserInfoServiceImpl extends BaseServiceImpl<User> implements UserInfoService {

    @Autowired
    public void setBaseDao(UserInfoDao userInfoDao) {
        super.setBaseDao(userInfoDao);
    }

}
