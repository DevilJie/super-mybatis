package com.hsj.supermybatis.core.dao.impl;

import com.hsj.supermybatis.base.bean.Pager;
import com.hsj.supermybatis.core.dao.BaseDao;
import com.hsj.supermybatis.core.mapper.BaseMapper;
import com.hsj.supermybatis.core.parser.SqlProviderConstants;
import com.hsj.supermybatis.core.tools.CoreUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/21/21:42
 */
public class BaseDaoImpl<T> implements BaseDao<T> {

    private Class<T>          entityClass;

    public BaseDaoImpl(){
        Class c = getClass();
        Type type = c.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
            this.entityClass = (Class<T>) parameterizedType[0];
        }
    }

    @Autowired
    BaseMapper baseMapper;

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
    public Long update(T t) {
        Map<String, Object> map = new HashMap<>();
        map.put(SqlProviderConstants.CLASS_NAME, entityClass);
        map.put(SqlProviderConstants.ENTITY, t);
        return baseMapper.update(map);
    }

    @Override
    public Pager getPager(Pager pager, T t) {
        Map<String, Object> map = new HashMap<>();
        map.put(SqlProviderConstants.CLASS_NAME, entityClass);
        map.put(SqlProviderConstants.ENTITY, t);
        map.put(SqlProviderConstants.PAGER, pager);
        List<T> ret = baseMapper.getPager(map).stream().map(item -> {
            T entity = null;
            try {
                entity = entityClass.getDeclaredConstructor().newInstance();
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
}
