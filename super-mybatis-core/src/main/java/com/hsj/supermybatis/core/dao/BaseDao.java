package com.hsj.supermybatis.core.dao;

import java.io.Serializable;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/21/21:41
 */
public interface BaseDao<T> {

    /**
     * 根据主键ID获取实体类
     * @param id
     * @return
     */
    T get(Serializable id);
}
