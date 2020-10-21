package com.cjxch.supermybatis.cache.redis.core;

import com.cjxch.supermybatis.cache.base.core.SuperMybatisCache;
import com.cjxch.supermybatis.cache.redis.init.SuperMybatisRedisConstants;
import com.cjxch.supermybatis.cache.redis.init.SuperMybatisRedisService;
import com.cjxch.supermybatis.cache.base.core.DbCacheSetting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 缓存处理类
 * @Author: 菜鸡小彩虹
 * @Date: 2020/10/01/13:01
 */
public class SuperMybatisRedisCache extends SuperMybatisCache {

    Logger logger = LoggerFactory.getLogger(this.getClass());


    public SuperMybatisRedisCache(DbCacheSetting setting){
        super(setting);
        logger.debug(String.format("【Super-Mybatis-Cache】Current cache plug-in: redis --> %s:%s", setting.getRedisHost(),setting.getRedisPort()));
    }

    @Override
    public void set(String key, Map<String, Object> value) {
        getRedisService().hmset(key, value);
    }

    @Override
    public void set(String key, Map<String, Object> value, long seconds) {
        getRedisService().hmset(key, value, seconds);
    }

    @Override
    public void setIfAbsent(String key, Map<String, Object> value) {
        if(!getRedisService().hasKey(key)) set(key, value);
    }

    @Override
    public void setIfAbsent(String key, Map<String, Object> value, long seconds) {
        if(!getRedisService().hasKey(key)) set(key, value, seconds);
    }

    @Override
    public void expires(String key, long seconds) {
        getRedisService().expire(key, seconds);
    }

    @Override
    public <T> T get(String key, String field, Class<T> t) {
        return (T) getRedisService().hget(key, field);
    }

    @Override
    public Object get(String key, String field) {
        return  getRedisService().hget(key, field);
    }

    @Override
    public void delete(String key) {
        getRedisService().del(key);
    }

    @Override
    public boolean isExits(String key) {
        return getRedisService().hasKey(key);
    }


    public SuperMybatisRedisService getRedisService(){
        if(SuperMybatisRedisConstants.superMybatisRedisService == null)
            SuperMybatisRedisConstants.superMybatisRedisService = SuperMybatisRedisConstants.applicationContext.getBean(SuperMybatisRedisService.class);
        return SuperMybatisRedisConstants.superMybatisRedisService;
    }

}
