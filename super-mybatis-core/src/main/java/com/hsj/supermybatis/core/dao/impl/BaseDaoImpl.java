package com.hsj.supermybatis.core.dao.impl;

import com.hsj.supermybatis.core.dao.BaseDao;
import com.hsj.supermybatis.core.mapper.BaseMapper;
import com.hsj.supermybatis.core.provider.SqlProviderConstants;
import com.hsj.supermybatis.core.tools.CoreUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        try{
            entity = (T) Class.forName(entityClass.getName()).newInstance();
        }catch(Exception e){
            entity = null;
        }
        return (T) CoreUtil.process(entity, map);
    }

    @Override
    public void insert(T t) {
        Map<String, Object> map = new HashMap<>();
        map.put(SqlProviderConstants.CLASS_NAME, entityClass);
        map.put(SqlProviderConstants.ENTITY, t);
        baseMapper.insert(map);
    }

    @Override
    public void batchInsert(List<T> l) {
        Map<String, Object> map = new HashMap<>();
        map.put(SqlProviderConstants.CLASS_NAME, entityClass);
        map.put(SqlProviderConstants.ENTITY_LIST, l);
        baseMapper.batchInsert(map);
    }
}
