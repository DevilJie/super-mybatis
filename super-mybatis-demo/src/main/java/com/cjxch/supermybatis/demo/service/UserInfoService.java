package com.cjxch.supermybatis.demo.service;

import com.cjxch.supermybatis.base.bean.Pager;
import com.cjxch.supermybatis.demo.entity.User;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/21/20:36
 */
public interface UserInfoService {
    User get(Serializable id);

    String insert(User user);

    String[] batchInsert(List<User> user);

    List<User> allList();

    /**
     * 删除
     * @param id
     * @return
     */
    Long delete(String id);

    Long update(User user);

    Pager getPager(Pager pager, User user);

    User get(User user);

    List<User> getList(User user);

    List<User> getList(User user, Pager.Order order, String orderBy);

    Long getCount(User user);
}
