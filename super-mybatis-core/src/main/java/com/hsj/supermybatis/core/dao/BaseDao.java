package com.hsj.supermybatis.core.dao;

import com.hsj.supermybatis.base.bean.Pager;

import java.io.Serializable;
import java.util.List;

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

    /**
     * 插入数据
     * @param t
     */
    Serializable insert(T t);

    /**
     * 插入数据
     * @param l
     */
    Serializable[] batchInsert(List<T> l);

    /**
     * 获取所有数据
     * @return
     */
    List<T> allList();

    /**
     * 根据主键ID删除
     * @param id
     */
    Long delete(Serializable id);

    /**
     * 更新数据
     * @param t
     */
    Long update(T t);

    /**
     * 分页查询
     * @param pager
     * @param t
     * @return
     */
    Pager getPager(Pager pager, T t);
}
