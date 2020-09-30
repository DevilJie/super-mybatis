package com.hsj.supermybatis.demo.dao.impl;

import com.alibaba.fastjson.JSON;
import com.hsj.supermybatis.core.dao.impl.BaseDaoImpl;
import com.hsj.supermybatis.demo.dao.UserInfoDao;
import com.hsj.supermybatis.demo.entity.User;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/21/22:20
 */
@Repository
public class UserInfoDaoImpl extends BaseDaoImpl<User> implements UserInfoDao {

    @Override
    public List<User> loadAll() {
        String sql = "select * from user_job";
        return getBatisSession().createQuery(sql).list(User.class);
    }
}
