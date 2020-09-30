package com.hsj.supermybatis.demo.service;

import com.hsj.supermybatis.base.bean.Pager;
import com.hsj.supermybatis.demo.entity.User;

import java.util.List;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/21/20:36
 */
public interface UserInfoService {
    void process();

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
