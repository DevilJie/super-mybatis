package com.hsj.supermybatis.demo.dao;


import com.hsj.supermybatis.core.dao.BaseDao;
import com.hsj.supermybatis.demo.entity.User;

import java.util.List;

public interface UserInfoDao extends BaseDao<User> {

    List<User> loadAll();

    List<User> loadByUser(User user);
}