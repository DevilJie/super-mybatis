package com.cjxch.supermybatis.core.dao;

import com.cjxch.supermybatis.base.bean.Pager;
import com.cjxch.supermybatis.core.tools.query.SmCriteria;

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
     * 根据主键ID删除
     * @param id
     */
    public Long delete(Serializable[] id);

    /**
     * 根据主键ID删除
     * @param id
     */
    public Long delete(List<Serializable> id);

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

    public T loadByColumn(String column, String val);

    public List<T> loadListByColumn(String column, String val);

    public List<T> loadListByColumn(String column, String val, Pager.Order order, String orderBy);

    public Long loadListCountByColumn(String column, String val);


    /****************************条件查询构造器相关方法 begin*********************************/

    /**
     * 分页查询
     * @param pager
     * @param smCriteria 查询条件构造器
     * @param t 返回的反类型对象
     * @return
     */
    public Pager getPager(Pager pager, SmCriteria smCriteria, Class<T> t);



    /**
     * 根据查询条件构造器查询
     * @param smCriteria 查询条件构造器
     * @param t 返回的反类型对象
     * @return
     */
    public List<T> getList(SmCriteria smCriteria, Class<T> t);

    /**
     * 根据查询条件构造器查询
     * @param t
     * @param order 排序类型
     * @param orderBy 排序字段
     * @return
     */
    public List<T> getList(SmCriteria smCriteria, Class<T> t, Pager.Order order, String orderBy);

    /**
     * 根据查询条件构造器查询数量
     * @param smCriteria 查询条件构造器
     * @param t 返回的反类型对象
     * @return
     */
    public Long getListCount(SmCriteria smCriteria, Class<T> t);


    /**
     * 分页查询
     * @param pager
     * @param smCriteria 查询条件构造器
     * @return
     */
    public Pager getPager(Pager pager, SmCriteria smCriteria);



    /**
     * 根据查询条件构造器查询
     * @param smCriteria 查询条件构造器
     * @return
     */
    public List<T> getList(SmCriteria smCriteria);


    /**
     * 根据查询条件构造器查询
     * @param smCriteria 查询条件构造器
     * @return
     */
    public T getObject(SmCriteria smCriteria);

    /**
     * 根据查询条件构造器查询
     * @param smCriteria 查询条件构造器
     * @return
     */
    public T getObject(SmCriteria smCriteria, Class<T> t);

    /**
     * 根据查询条件构造器查询
     * @param order 排序类型
     * @param orderBy 排序字段
     * @return
     */
    public List<T> getList(SmCriteria smCriteria, Pager.Order order, String orderBy);

    /**
     * 根据查询条件构造器查询数量
     * @param smCriteria 查询条件构造器
     * @return
     */
    public Long getListCount(SmCriteria smCriteria);


    /**
     * 根据主键ID删除
     * @param smCriteria 查询条件构造器
     */
    public Long delete(SmCriteria smCriteria);


    /****************************条件查询构造器相关方法 end*********************************/
}
