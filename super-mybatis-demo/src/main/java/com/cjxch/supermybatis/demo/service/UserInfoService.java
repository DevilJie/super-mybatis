package com.cjxch.supermybatis.demo.service;

import com.cjxch.supermybatis.base.bean.Pager;
import com.cjxch.supermybatis.core.tools.query.SmCriteria;
import com.cjxch.supermybatis.demo.entity.User;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/21/20:36
 */
public interface UserInfoService {
    User get(Serializable id);

    Serializable insert(User user);

    Serializable[] batchInsert(List<User> user);

    List<User> allList();

    /**
     * 删除
     * @param id
     * @return
     */
    Long delete(String id);

    /**
     * 删除
     * @return
     */
    Long delete(SmCriteria criteria);

    Long update(User user);

    Pager getPager(Pager pager, User user);

    User get(User user);

    List<User> getList(User user);

    List<User> getList(User user, Pager.Order order, String orderBy);

    Long getCount(User user);


    User loadByColumn(String column, String val);

    List<User> loadListByColumn(String column, String val);

    List<User> loadListByColumn(String column, String val, Pager.Order order, String orderBy);

    Long loadListCountByColumn(String column, String val);

    List<User> loadListByIds(Serializable[] ids);


    List<User> getList(SmCriteria criteria);
}
