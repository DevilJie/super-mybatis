package com.cjxch.supermybatis.core.Service.impl;

import com.cjxch.supermybatis.base.bean.Pager;
import com.cjxch.supermybatis.core.Service.BaseService;
import com.cjxch.supermybatis.core.dao.BaseDao;
import com.cjxch.supermybatis.core.tools.query.SmCriteria;

import java.io.Serializable;
import java.util.List;

/**
 * @Author Czy
 * @Description //TODO $
 * @Date $ $
 * @Param $
 * @return $
 **/
public class BaseServiceImpl<T> implements BaseService<T> {

    private BaseDao<T> baseDao;

    public void setBaseDao(BaseDao<T> baseDao){
        this.baseDao = baseDao;
    }


    @Override
    public T get(Serializable id) {
        return baseDao.get(id);
    }

    @Override
    public Serializable insert(T t) {
        return baseDao.insert(t);
    }

    @Override
    public Serializable[] batchInsert(List<T> l) {
        return baseDao.batchInsert(l);
    }

    @Override
    public List<T> allList() {
        return baseDao.allList();
    }

    @Override
    public Long delete(Serializable id) {
        return baseDao.delete(id);
    }

    @Override
    public Long delete(Serializable[] id) {
        return baseDao.delete(id);
    }

    @Override
    public Long delete(List<Serializable> id) {
        return baseDao.delete(id);
    }

    @Override
    public Long update(T t) {
        return baseDao.update(t);
    }

    @Override
    public Pager getPager(Pager pager, T t) {
        return baseDao.getPager(pager, t);
    }

    @Override
    public T getByEntity(T t) {
        return baseDao.getByEntity(t);
    }

    @Override
    public List<T> getList(T t) {
        return baseDao.getList(t);
    }

    @Override
    public List<T> getList(T t, Pager.Order order, String orderBy) {
        return baseDao.getList(t, order, orderBy);
    }

    @Override
    public Long getListCount(T t) {
        return baseDao.getListCount(t);
    }

    @Override
    public T loadByColumn(String column, String val) {
        return baseDao.loadByColumn(column, val);
    }

    @Override
    public List<T> loadListByColumn(String column, String val) {
        return baseDao.loadListByColumn(column, val);
    }

    @Override
    public List<T> loadListByColumn(String column, String val, Pager.Order order, String orderBy) {
        return baseDao.loadListByColumn(column, val, order, orderBy);
    }

    @Override
    public Long loadListCountByColumn(String column, String val) {
        return baseDao.loadListCountByColumn(column, val);
    }

    @Override
    public Pager getPager(Pager pager, SmCriteria smCriteria, Class<T> t) {
        return baseDao.getPager(pager, smCriteria, t);
    }

    @Override
    public List<T> getList(SmCriteria smCriteria, Class<T> t) {
        return baseDao.getList(smCriteria, t);
    }

    @Override
    public List<T> getList(SmCriteria smCriteria, Class<T> t, Pager.Order order, String orderBy) {
        return baseDao.getList(smCriteria, t, order, orderBy);
    }

    @Override
    public Long getListCount(SmCriteria smCriteria, Class<T> t) {
        return baseDao.getListCount(smCriteria, t);
    }

    @Override
    public Pager getPager(Pager pager, SmCriteria smCriteria) {
        return baseDao.getPager(pager, smCriteria);
    }

    @Override
    public List<T> getList(SmCriteria smCriteria) {
        return baseDao.getList(smCriteria);
    }

    @Override
    public T getObject(SmCriteria smCriteria) {
        return baseDao.getObject(smCriteria);
    }

    @Override
    public T getObject(SmCriteria smCriteria, Class<T> t) {
        return baseDao.getObject(smCriteria, t);
    }

    @Override
    public List<T> getList(SmCriteria smCriteria, Pager.Order order, String orderBy) {
        return baseDao.getList(smCriteria, order, orderBy);
    }

    @Override
    public Long getListCount(SmCriteria smCriteria) {
        return baseDao.getListCount(smCriteria);
    }

    @Override
    public Long delete(SmCriteria smCriteria) {
        return baseDao.delete(smCriteria);
    }
}
