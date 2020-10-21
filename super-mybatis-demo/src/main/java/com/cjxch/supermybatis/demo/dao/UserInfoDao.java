package com.cjxch.supermybatis.demo.dao;


import com.cjxch.supermybatis.cache.base.annotation.CacheSet;
import com.cjxch.supermybatis.core.dao.BaseDao;
import com.cjxch.supermybatis.demo.entity.User;

import java.util.List;

public interface UserInfoDao extends BaseDao<User> {

    @CacheSet(key = "#ENTITY_CLASS#-#PRIMARYKEY#", expires = 1000 * 60 * 10)
    List<User> loadAll();

    List<User> loadByUser(User user);
}