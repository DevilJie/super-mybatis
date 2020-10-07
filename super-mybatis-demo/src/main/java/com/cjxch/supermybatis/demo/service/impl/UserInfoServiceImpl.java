package com.cjxch.supermybatis.demo.service.impl;

import com.cjxch.supermybatis.base.bean.Pager;
import com.cjxch.supermybatis.demo.entity.User;
import com.cjxch.supermybatis.demo.service.UserInfoService;
import com.cjxch.supermybatis.demo.dao.UserInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/21/20:37
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    UserInfoDao userInfoDao;

    @Override
    public User get(Serializable id) {
        return userInfoDao.get(id);
    }

    @Override
    public String insert(User user) {
        return String.valueOf(userInfoDao.insert(user));
    }

    @Override
    public String[] batchInsert(List<User> user) {
        return (String[])userInfoDao.batchInsert(user);
    }

    @Override
    public List<User> allList() {
        return userInfoDao.allList();
        /**
         * 封装自定义的查询sql
         */
//        return userInfoDao.loadAll();
    }

    @Override
    public Long delete(String id) {
        return userInfoDao.delete(id);
    }

    @Override
    public Long update(User user) {
        return userInfoDao.update(user);
    }

    @Override
    public Pager getPager(Pager pager, User user) {
        return userInfoDao.getPager(pager, user);
    }


    @Override
    public User get(User user) {
        return userInfoDao.getByEntity(user);
    }

    @Override
    public List<User> getList(User user) {
//        return userInfoDao.getList(user);
        return userInfoDao.loadByUser(user);
    }

    @Override
    public List<User> getList(User user, Pager.Order order, String orderBy) {
        return userInfoDao.getList(user, order, orderBy);
    }

    @Override
    public Long getCount(User user) {
        return userInfoDao.getListCount(user);
    }
}
