package com.cjxch.supermybatis.cache.memcached.core;

import com.cjxch.supermybatis.cache.base.core.DbCacheSetting;
import com.cjxch.supermybatis.cache.base.core.SuperMybatisCache;
import com.cjxch.supermybatis.cache.base.core.SuperMybatisCacheConstants;
import com.cjxch.supermybatis.cache.memcached.init.SuperMybatisMemcachedConstants;
import com.whalin.MemCached.MemCachedClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;

/**
 * 缓存处理类
 * @Author: 菜鸡小彩虹
 * @Date: 2020/10/01/13:01
 */
public class SuperMybatisMemcachedCache extends SuperMybatisCache {

    Logger logger = LoggerFactory.getLogger(this.getClass());


    public SuperMybatisMemcachedCache(DbCacheSetting setting){
        super(setting);
        logger.debug(String.format("【Super-Mybatis-Cache】Current cache plug-in: Memcached --> %s:%s", setting.getMemcachedHost(),setting.getMemcachedPort()));
    }

    @Override
    public void set(String key, Map<String, Object> value) {
        getMemcachedService().set(key, value);
    }

    @Override
    public void set(String key, Map<String, Object> value, long seconds) {
        getMemcachedService().set(key, value, new Date(seconds*1000));
    }

    @Override
    public void setIfAbsent(String key, Map<String, Object> value) {
        if(!getMemcachedService().keyExists(key)) set(key, value);
    }

    @Override
    public void setIfAbsent(String key, Map<String, Object> value, long seconds) {
        if(!getMemcachedService().keyExists(key)) set(key, value, seconds);
    }

    @Override
    public void expires(String key, long seconds) {
        Map obj = (Map)getMemcachedService().get(key);
        set(key, obj, seconds);
    }

    @Override
    public <T> T get(String key, String field, Class<T> t) {
        Map<String, Object> val = (Map<String, Object>)getMemcachedService().get(key);
        if(val == null) return null;
        return  (T) val.get(field);
    }

    @Override
    public Object get(String key, String field) {
        Map<String, Object> val = (Map<String, Object>)getMemcachedService().get(key);
        if(val == null) return null;
        return  val.get(field);
    }

    @Override
    public void delete(String key) {
        getMemcachedService().delete(key);
    }

    @Override
    public boolean isExits(String key) {
        return getMemcachedService().keyExists(key);
    }


    public MemCachedClient getMemcachedService(){
        if(SuperMybatisMemcachedConstants.memCachedClient == null)
            SuperMybatisMemcachedConstants.memCachedClient = SuperMybatisCacheConstants.applicationContext.getBean(MemCachedClient.class);
        return SuperMybatisMemcachedConstants.memCachedClient;
    }

}
