package com.hsj.supermybatis.demo.service;

import com.hsj.supermybatis.demo.entity.User;

import java.util.List;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/21/20:36
 */
public interface UserInfoService {
    void process();

    void insert(User user);

    void batchInsert(List<User> user);
}
