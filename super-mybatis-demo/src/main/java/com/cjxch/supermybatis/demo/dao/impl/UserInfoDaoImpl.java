package com.cjxch.supermybatis.demo.dao.impl;

import com.cjxch.supermybatis.cache.base.annotation.CacheEvict;
import com.cjxch.supermybatis.cache.base.annotation.CacheSet;
import com.cjxch.supermybatis.core.dao.impl.BaseDaoImpl;
import com.cjxch.supermybatis.demo.dao.UserInfoDao;
import com.cjxch.supermybatis.demo.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/21/22:20
 */
@Repository
public class UserInfoDaoImpl extends BaseDaoImpl<User> implements UserInfoDao {

    @Override
    public List<User> loadAll() {
        String sql = "select * from user_info";
        return getBatisSession().createQuery(sql).list(User.class);
    }

    @Override
    @CacheSet(key="#user.realName", group = "userInfo")
    public List<User> loadByUser(User user) {
        Mybatis
        StringBuffer sql = new StringBuffer("select * from user_info u where 1=1");
        if(user != null){
            if(!StringUtils.isEmpty(user.getRealName())) sql.append(" and u.real_name = #{ui.realName}");
        }
        return getBatisSession().createQuery(sql.toString()).setParameter("ui", user).list(User.class);
    }

    @Override
    @CacheSet(key="'user-info:' + #id")
    public User get(Serializable id) {
        return super.get(id);
    }

    @Override
    @CacheEvict(key="'user-info:' + #id")
    public Long delete(Serializable id) {
        return super.delete(id);
    }
}
