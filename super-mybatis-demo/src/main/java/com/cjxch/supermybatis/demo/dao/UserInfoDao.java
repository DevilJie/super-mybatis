package com.cjxch.supermybatis.demo.dao;


import com.cjxch.supermybatis.core.dao.BaseDao;
import com.cjxch.supermybatis.demo.entity.User;

import java.util.List;

public interface UserInfoDao extends BaseDao<User> {

    List<User> loadAll();

    List<User> loadByUser(User user);
}