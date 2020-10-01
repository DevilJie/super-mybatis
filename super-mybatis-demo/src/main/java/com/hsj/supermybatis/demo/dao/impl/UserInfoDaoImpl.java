package com.hsj.supermybatis.demo.dao.impl;

import com.hsj.supermybatis.base.bean.Pager;
import com.hsj.supermybatis.core.dao.impl.BaseDaoImpl;
import com.hsj.supermybatis.demo.dao.UserInfoDao;
import com.hsj.supermybatis.demo.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

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

    @Override
    public List<User> loadByUser(User user) {
        StringBuffer sql = new StringBuffer("select * from test_user u where 1=1");
        if(user != null){
            if(!StringUtils.isEmpty(user.getName())) sql.append(" and u.name = #{ui.name}");
        }
        return getBatisSession().createQuery(sql.toString()).setParameter("ui", user).list(User.class);
    }

    @Override
    public Pager getPager(Pager pager, User user) {
        StringBuffer sql = new StringBuffer("select * from test_user u where 1=1");
        if(user != null){
            if(!StringUtils.isEmpty(user.getName())) sql.append(" and u.name = #{ui.name}");
        }
        return getBatisSession().createQuery(sql.toString()).setParameter("ui", user).queryPager(pager, User.class);
    }
}
