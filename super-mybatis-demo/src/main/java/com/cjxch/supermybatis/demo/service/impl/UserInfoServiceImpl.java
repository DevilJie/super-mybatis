package com.cjxch.supermybatis.demo.service.impl;

import com.cjxch.supermybatis.base.bean.Pager;
import com.cjxch.supermybatis.core.tools.query.SmCriteria;
import com.cjxch.supermybatis.demo.entity.User;
import com.cjxch.supermybatis.demo.service.UserInfoService;
import com.cjxch.supermybatis.demo.dao.UserInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
    public Serializable insert(User user) {
        return userInfoDao.insert(user);
    }

    @Override
    public Serializable[] batchInsert(List<User> user) {
        return userInfoDao.batchInsert(user);
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
    public Long delete(SmCriteria criteria) {
        return userInfoDao.delete(criteria);
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
    @Cacheable("userCache")
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

    @Override
    public User loadByColumn(String column, String val) {
        return userInfoDao.loadByColumn(column, val);
    }

    @Override
    public List<User> loadListByColumn(String column, String val) {
        return userInfoDao.loadListByColumn(column, val);
    }

    @Override
    public List<User> loadListByColumn(String column, String val, Pager.Order order, String orderBy) {
        return userInfoDao.loadListByColumn(column, val, order, orderBy);
    }

    @Override
    public Long loadListCountByColumn(String column, String val) {
        return userInfoDao.loadListCountByColumn(column, val);
    }

    @Override
    public List<User> loadListByIds(Serializable[] ids) {
        return userInfoDao.loadByIds(ids);
    }

    @Override
    public List<User> getList(SmCriteria criteria) {
        return userInfoDao.getList(criteria);
    }
}
