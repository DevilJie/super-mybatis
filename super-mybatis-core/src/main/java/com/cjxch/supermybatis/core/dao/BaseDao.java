package com.cjxch.supermybatis.core.dao;

import com.cjxch.supermybatis.base.bean.Pager;

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
    public T get(Serializable id);

    /**
     * 插入数据
     * @param t
     */
    public Serializable insert(T t);

    /**
     * 插入数据
     * @param l
     */
    public Serializable[] batchInsert(List<T> l);

    /**
     * 获取所有数据
     * @return
     */
    public List<T> allList();

    /**
     * 根据主键ID删除
     * @param id
     */
    public Long delete(Serializable id);

    /**
     * 更新数据
     * @param t
     */
    public Long update(T t);

    /**
     * 分页查询
     * @param pager
     * @param t
     * @return
     */
    public Pager getPager(Pager pager, T t);

    /**
     * 根据对象查询
     * @param t
     * @return
     */
    public T getByEntity(T t);

    /**
     * 根据对象查询列表
     * @param t
     * @return
     */
    public List<T> getList(T t);

    /**
     * 根据对象查询列表
     * @param t
     * @return
     */
    public List<T> getList(T t, Pager.Order order, String orderBy);

    /**
     * 根据对象获取总数据量
     * @return
     */
    public Long getListCount(T t);
}
