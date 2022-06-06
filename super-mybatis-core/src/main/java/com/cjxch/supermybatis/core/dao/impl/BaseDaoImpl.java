package com.cjxch.supermybatis.core.dao.impl;

import com.cjxch.supermybatis.base.bean.Pager;
import com.cjxch.supermybatis.core.actuator.SuperMybatisBaseSession;
import com.cjxch.supermybatis.core.actuator.simple.SuperMybatisSimpleSession;
import com.cjxch.supermybatis.core.mapper.BaseMapper;
import com.cjxch.supermybatis.core.parser.SqlProviderConstants;
import com.cjxch.supermybatis.core.tools.CoreUtil;
import com.cjxch.supermybatis.core.dao.BaseDao;
import com.cjxch.supermybatis.core.tools.SuperMybatisAssert;
import com.cjxch.supermybatis.core.tools.query.SmCriteria;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/21/21:42
 */
public class BaseDaoImpl<T> implements BaseDao<T> {

    private Class<T>          entityClass;

    private SuperMybatisBaseSession superMybatisBaseSession;

    public BaseDaoImpl(){
        Class c = getClass();
        Type type = c.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
            this.entityClass = (Class<T>) parameterizedType[0];
        }
    }

    @Autowired
    private BaseMapper baseMapper;

    @Override
    public T get(Serializable id) {
        Map<String, Object> map = new HashMap<>();
        map.put(SqlProviderConstants.CLASS_NAME, entityClass);
        map.put("id", id);
        map = baseMapper.get(map);
        T entity = null;
        try {
            entity = entityClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map == null ? null : (T) CoreUtil.process(entity, map);
    }

    @Override
    public Serializable insert(T t) {
        Map<String, Object> map = new HashMap<>();
        map.put(SqlProviderConstants.CLASS_NAME, entityClass);
        map.put(SqlProviderConstants.ENTITY, t);
        baseMapper.insert(map);
        return (Serializable) map.get(SqlProviderConstants.PRIMARY_KEY_VALUE);
    }

    @Override
    public Serializable[] batchInsert(List<T> l) {
        Map<String, Object> map = new HashMap<>();
        map.put(SqlProviderConstants.CLASS_NAME, entityClass);
        map.put(SqlProviderConstants.ENTITY_LIST, l);
        baseMapper.batchInsert(map);
        return (Serializable[]) map.get(SqlProviderConstants.PRIMARY_KEY_VALUE);
    }

    @Override
    public List<T> allList() {
        Map<String, Object> map = new HashMap<>();
        map.put(SqlProviderConstants.CLASS_NAME, entityClass);
        return baseMapper.getAllList(map).stream().map(item -> {
            T entity = null;
            try {
                entity = entityClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            T t = (T) CoreUtil.process(entity, item);
            return t;
        }).collect(Collectors.toList());
    }

    @Override
    public Long delete(Serializable id) {
        Map<String, Object> map = new HashMap<>();
        map.put(SqlProviderConstants.CLASS_NAME, entityClass);
        map.put("id", id);
        return baseMapper.delete(map);
    }

    @Override
    public Long delete(Serializable[] id) {
        AtomicReference<Long> ret = new AtomicReference<>(0l);
        Arrays.asList(id).forEach(e -> {
            ret.updateAndGet(v -> v + delete(e));
        });
        return ret.get();
    }

    @Override
    public Long update(T t) {
        Map<String, Object> map = new HashMap<>();
        map.put(SqlProviderConstants.CLASS_NAME, entityClass);
        map.put(SqlProviderConstants.ENTITY, t);
        return baseMapper.update(map);
    }

    @Override
    public Pager getPager(Pager pager, T t) {
        SuperMybatisAssert.check(t != null, "Sorry, Query object cannot be empty.");
        Map<String, Object> map = new HashMap<>();
        map.put(SqlProviderConstants.CLASS_NAME, entityClass);
        map.put(SqlProviderConstants.ENTITY, t);
        map.put(SqlProviderConstants.PAGER, pager);
        map.put(SqlProviderConstants.ORDER, pager.getOrder());
        map.put(SqlProviderConstants.ORDER_BY, pager.getOrderBy());
        List<T> ret = baseMapper.getPager(map).stream().map(item -> {
            T entity = null;
            try {
                entity = (T) t.getClass().getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            T tt = (T) CoreUtil.process(entity, item);
            return tt;
        }).collect(Collectors.toList());

        Long totalCount = baseMapper.getPagerCount(map);

        pager.setResult(ret);
        pager.setTotalCount(totalCount==null?0:totalCount.intValue());
        return pager;
    }

    @Override
    public T getByEntity(T t) {
        SuperMybatisAssert.check(t != null, "Sorry, Query object cannot be empty.");
        Map<String, Object> map = new HashMap<>();
        map.put(SqlProviderConstants.CLASS_NAME, entityClass);
        map.put(SqlProviderConstants.ENTITY, t);
        Pager pager = new Pager();
        pager.setPageSize(2);
        map.put(SqlProviderConstants.PAGER, pager);
        map.put(SqlProviderConstants.ORDER, pager.getOrder());
        map.put(SqlProviderConstants.ORDER_BY, pager.getOrderBy());
        List<T> ret = baseMapper.getPager(map).stream().map(item -> {
            T entity = null;
            try {
                entity = (T) t.getClass().getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            T tt = (T) CoreUtil.process(entity, item);
            return tt;
        }).collect(Collectors.toList());
        if(ret == null || ret.size() == 0) return null;
        SuperMybatisAssert.check(ret.size() == 1, "The data you query is not unique, Please invoke method getList(T).");
        return ret.get(0);
    }

    @Override
    public List<T> getList(T t) {
        SuperMybatisAssert.check(t != null, "Sorry, Query object cannot be empty.");
        Map<String, Object> map = new HashMap<>();
        map.put(SqlProviderConstants.CLASS_NAME, entityClass);
        map.put(SqlProviderConstants.ENTITY, t);
        Pager pager = new Pager();
        map.put(SqlProviderConstants.PAGER, pager);
        map.put(SqlProviderConstants.ORDER, pager.getOrder());
        map.put(SqlProviderConstants.ORDER_BY, pager.getOrderBy());
        List<T> ret = baseMapper.getObjectList(map).stream().map(item -> {
            T entity = null;
            try {
                entity = (T) t.getClass().getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            T tt = (T) CoreUtil.process(entity, item);
            return tt;
        }).collect(Collectors.toList());
        return ret;
    }

    @Override
    public List<T> getList(T t, Pager.Order order, String orderBy) {
        SuperMybatisAssert.check(t != null, "Sorry, Query object cannot be empty.");
        Map<String, Object> map = new HashMap<>();
        map.put(SqlProviderConstants.CLASS_NAME, entityClass);
        map.put(SqlProviderConstants.ENTITY, t);
        Pager pager = new Pager();
        map.put(SqlProviderConstants.PAGER, pager);
        map.put(SqlProviderConstants.ORDER, order);
        map.put(SqlProviderConstants.ORDER_BY, orderBy);
        List<T> ret = baseMapper.getObjectList(map).stream().map(item -> {
            T entity = null;
            try {
                entity = (T) t.getClass().getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            T tt = (T) CoreUtil.process(entity, item);
            return tt;
        }).collect(Collectors.toList());
        return ret;
    }

    @Override
    public Long getListCount(T t) {
        SuperMybatisAssert.check(t != null, "Sorry, Query object cannot be empty.");
        Map<String, Object> map = new HashMap<>();
        map.put(SqlProviderConstants.CLASS_NAME, entityClass);
        map.put(SqlProviderConstants.ENTITY, t);
        Pager pager = new Pager();
        map.put(SqlProviderConstants.PAGER, pager);
        map.put(SqlProviderConstants.ORDER, pager.getOrder());
        map.put(SqlProviderConstants.ORDER_BY, pager.getOrderBy());
        return baseMapper.getObjectListCount(map);
    }

    @Override
    public T loadByColumn(String column, String val) {
        SuperMybatisAssert.check(column != null && val != null, "Sorry, The properties and values of the query cannot be empty.");
        Map<String, Object> map = new HashMap<>();
        map.put(SqlProviderConstants.CLASS_NAME, entityClass);
        map.put(SqlProviderConstants.QUERY_COLUMN, column);
        map.put(SqlProviderConstants.QUERY_VAL, val);
        List<T> ret = baseMapper.getListByColumn(map).stream().map(item -> {
            T entity = null;
            try {
                entity = (T) entityClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            T tt = (T) CoreUtil.process(entity, item);
            return tt;
        }).collect(Collectors.toList());

        if(ret == null || ret.size() == 0) return null;
        SuperMybatisAssert.check(ret.size() == 1, "The data you query is not unique, Please invoke method loadListByColumn(column, val).");
        return ret.get(0);
    }

    @Override
    public List<T> loadListByColumn(String column, String val) {
        SuperMybatisAssert.check(column != null && val != null, "Sorry, The properties and values of the query cannot be empty.");
        Map<String, Object> map = new HashMap<>();
        map.put(SqlProviderConstants.CLASS_NAME, entityClass);
        map.put(SqlProviderConstants.QUERY_COLUMN, column);
        map.put(SqlProviderConstants.QUERY_VAL, val);
        List<T> ret = baseMapper.getListByColumn(map).stream().map(item -> {
            T entity = null;
            try {
                entity = (T) entityClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            T tt = (T) CoreUtil.process(entity, item);
            return tt;
        }).collect(Collectors.toList());

        if(ret == null || ret.size() == 0) return new ArrayList<>();
        return ret;
    }

    @Override
    public List<T> loadListByColumn(String column, String val, Pager.Order order, String orderBy) {
        SuperMybatisAssert.check(column != null && val != null, "Sorry, The properties and values of the query cannot be empty.");
        Map<String, Object> map = new HashMap<>();
        map.put(SqlProviderConstants.CLASS_NAME, entityClass);
        map.put(SqlProviderConstants.QUERY_COLUMN, column);
        map.put(SqlProviderConstants.QUERY_VAL, val);
        map.put(SqlProviderConstants.ORDER, order);
        map.put(SqlProviderConstants.ORDER_BY, orderBy);
        List<T> ret = baseMapper.getListByColumn(map).stream().map(item -> {
            T entity = null;
            try {
                entity = (T) entityClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            T tt = (T) CoreUtil.process(entity, item);
            return tt;
        }).collect(Collectors.toList());

        if(ret == null || ret.size() == 0) return new ArrayList<>();
        return ret;
    }

    @Override
    public Long loadListCountByColumn(String column, String val) {
        SuperMybatisAssert.check(column != null && val != null, "Sorry, The properties and values of the query cannot be empty.");
        Map<String, Object> map = new HashMap<>();
        map.put(SqlProviderConstants.CLASS_NAME, entityClass);
        map.put(SqlProviderConstants.QUERY_COLUMN, column);
        map.put(SqlProviderConstants.QUERY_VAL, val);
        return baseMapper.getListCountByColumn(map);
    }

    @Override
    public Pager getPager(Pager pager, SmCriteria smCriteria, Class<T> t) {
        SuperMybatisAssert.check(smCriteria != null, "Sorry, The Object SmCriteria cannot be empty.");
        Map<String, Object> map = new HashMap<>();
        map.put(SqlProviderConstants.CLASS_NAME, entityClass);
        map.put(SqlProviderConstants.SM_CRITERIA, smCriteria);
        map.put(SqlProviderConstants.PAGER, pager);
        map.put(SqlProviderConstants.ORDER, pager.getOrder());
        map.put(SqlProviderConstants.ORDER_BY, pager.getOrderBy());

        List<T> ret = baseMapper.getPager(map).stream().map(item -> {
            T entity = null;
            try {
                entity = t.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            T tt = (T) CoreUtil.process(entity, item);
            return tt;
        }).collect(Collectors.toList());

        Long totalCount = baseMapper.getPagerCount(map);

        pager.setResult(ret);
        pager.setTotalCount(totalCount==null?0:totalCount.intValue());
        return pager;
    }

    @Override
    public List<T> getList(SmCriteria smCriteria, Class<T> t) {
        SuperMybatisAssert.check(smCriteria != null, "Sorry, The Object SmCriteria cannot be empty.");
        Map<String, Object> map = new HashMap<>();
        map.put(SqlProviderConstants.CLASS_NAME, entityClass);
        map.put(SqlProviderConstants.SM_CRITERIA, smCriteria);
        Pager pager = new Pager();
        map.put(SqlProviderConstants.PAGER, pager);
        map.put(SqlProviderConstants.ORDER, pager.getOrder());
        map.put(SqlProviderConstants.ORDER_BY, pager.getOrderBy());
        List<T> ret = baseMapper.getObjectList(map).stream().map(item -> {
            T entity = null;
            try {
                entity = t.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            T tt = (T) CoreUtil.process(entity, item);
            return tt;
        }).collect(Collectors.toList());
        return ret;
    }

    @Override
    public List<T> getList(SmCriteria smCriteria, Class<T> t, Pager.Order order, String orderBy) {
        SuperMybatisAssert.check(smCriteria != null, "Sorry, The Object SmCriteria cannot be empty.");
        Map<String, Object> map = new HashMap<>();
        map.put(SqlProviderConstants.CLASS_NAME, entityClass);
        map.put(SqlProviderConstants.ENTITY, t);
        Pager pager = new Pager();
        map.put(SqlProviderConstants.PAGER, pager);
        map.put(SqlProviderConstants.ORDER, order);
        map.put(SqlProviderConstants.ORDER_BY, orderBy);
        List<T> ret = baseMapper.getObjectList(map).stream().map(item -> {
            T entity = null;
            try {
                entity = t.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            T tt = (T) CoreUtil.process(entity, item);
            return tt;
        }).collect(Collectors.toList());
        return ret;
    }

    @Override
    public Long getListCount(SmCriteria smCriteria, Class<T> t) {
        SuperMybatisAssert.check(smCriteria != null, "Sorry, The Object SmCriteria cannot be empty.");
        Map<String, Object> map = new HashMap<>();
        map.put(SqlProviderConstants.CLASS_NAME, entityClass);
        map.put(SqlProviderConstants.SM_CRITERIA, smCriteria);
        Pager pager = new Pager();
        map.put(SqlProviderConstants.PAGER, pager);
        map.put(SqlProviderConstants.ORDER, pager.getOrder());
        map.put(SqlProviderConstants.ORDER_BY, pager.getOrderBy());
        return baseMapper.getObjectListCount(map);
    }

    @Override
    public Pager getPager(Pager pager, SmCriteria smCriteria) {
        return getPager(pager, smCriteria, getEntityClass());
    }

    @Override
    public List<T> getList(SmCriteria smCriteria) {
        return getList(smCriteria, getEntityClass());
    }

    @Override
    public List<T> getList(SmCriteria smCriteria, Pager.Order order, String orderBy) {
        return getList(smCriteria, getEntityClass(), order, orderBy);
    }

    @Override
    public Long getListCount(SmCriteria smCriteria) {
        return getListCount(smCriteria, getEntityClass());
    }


    public SuperMybatisBaseSession getBatisSession() {
        if(superMybatisBaseSession == null) superMybatisBaseSession = new SuperMybatisSimpleSession(baseMapper);
        return this.superMybatisBaseSession;
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }
}
