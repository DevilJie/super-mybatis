package com.cjxch.supermybatis.cache.ehcache.core;

import com.cjxch.supermybatis.cache.base.core.DbCacheSetting;
import com.cjxch.supermybatis.cache.base.core.SuperMybatisCache;
import com.cjxch.supermybatis.cache.base.core.SuperMybatisCacheConstants;
import com.cjxch.supermybatis.cache.ehcache.init.EhcacheService;
import com.cjxch.supermybatis.cache.ehcache.init.SuperMybatisEhcacheConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 缓存处理类
 * @Author: 菜鸡小彩虹
 * @Date: 2020/10/01/13:01
 */
public class SuperMybatisEhcacheCache extends SuperMybatisCache {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public SuperMybatisEhcacheCache(DbCacheSetting setting){
        super(setting);
        logger.debug("【Super-Mybatis-Cache】Current cache plug-in: Ehcache");
    }

    @Override
    public void set(String key, Map<String, Object> value) {
        getEhcacheService().set(key, value);
    }

    @Override
    public void set(String key, Map<String, Object> value, long seconds) {
        getEhcacheService().set(key, value, seconds);
    }

    @Override
    public void setIfAbsent(String key, Map<String, Object> value) {
        if(!getEhcacheService().keyExits(key)) set(key, value);
    }

    @Override
    public void setIfAbsent(String key, Map<String, Object> value, long seconds) {
        if(!getEhcacheService().keyExits(key)) set(key, value, seconds);
    }

    @Override
    public void expires(String key, long seconds) {
        Map obj = (Map)getEhcacheService().get(key);
        set(key, obj, seconds);
    }

    @Override
    public <T> T get(String key, String field, Class<T> t) {
        Map<String, Object> val = (Map<String, Object>)getEhcacheService().get(key);
        if(val == null) return null;
        return  (T) val.get(field);
    }

    @Override
    public Object get(String key, String field) {
        Map<String, Object> val = (Map<String, Object>)getEhcacheService().get(key);
        if(val == null) return null;
        return val.get(field);
    }

    @Override
    public void delete(String key) {
        getEhcacheService().remove(key);
    }

    @Override
    public boolean isExits(String key) {
        return getEhcacheService().keyExits(key);
    }


    public EhcacheService getEhcacheService(){
        if(SuperMybatisEhcacheConstants.ehcacheService == null)
            SuperMybatisEhcacheConstants.ehcacheService = SuperMybatisCacheConstants.applicationContext.getBean(EhcacheService.class);
        return SuperMybatisEhcacheConstants.ehcacheService;
    }

}
