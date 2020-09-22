package com.hsj.supermybatis.demo.provider;

import com.hsj.supermybatis.demo.entity.User;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/21/21:33
 */
public class SqlProvider {

    public String findById(String id){
        return "select * from user where id=#{id}";
    }
}
