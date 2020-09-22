package com.hsj.supermybatis.demo.service.impl;

import com.hsj.supermybatis.demo.entity.User;
import com.hsj.supermybatis.demo.dao.UserInfoDao;
import com.hsj.supermybatis.demo.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/21/20:37
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    UserInfoDao userInfoDao;

    @Override
    public void process() {
        User u = userInfoDao.get("1");
        System.out.println(u);
    }
}
