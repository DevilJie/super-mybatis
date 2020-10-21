package com.cjxch.supermybatis.cache.base.core;

import com.cjxch.supermybatis.base.enu.DbCacheType;
import com.cjxch.supermybatis.base.exception.SuperMybatisException;
import com.cjxch.supermybatis.core.tools.SuperMybatisAssert;

import java.lang.reflect.Constructor;
import java.util.Map;

/**
 * 缓存处理类
 * @Author: 菜鸡小彩虹
 * @Date: 2020/10/01/13:01
 */
public abstract class SuperMybatisCache {

    public static void init(DbCacheSetting setting) throws SuperMybatisException {
        if(SuperMybatisCacheConstants.superMybatisCache == null) {
            try {
                Class clazz = null;
                if (setting.getDbCacheType() == DbCacheType.Redis) {
                    clazz = Class.forName("com.cjxch.supermybatis.cache.redis.core.SuperMybatisRedisCache");
                }else if(setting.getDbCacheType() == DbCacheType.Memcached){
                    clazz = Class.forName("com.cjxch.supermybatis.cache.memcached.core.SuperMybatisMemcachedCache");
                }

                SuperMybatisAssert.check(clazz != null, "Unknown dbCacheType");

                Constructor cons = clazz.getConstructor(DbCacheSetting.class);
                SuperMybatisCacheConstants.superMybatisCache = (SuperMybatisCache) cons.newInstance(setting);
            }catch(Exception e){
                throw new SuperMybatisException(e);
            }
        }
    }

    public SuperMybatisCache(DbCacheSetting setting){}

    /**
     * 写入缓存
     * @param key 缓存键
     * @param value 缓存值
     */
    public abstract void set(String key, Map<String, Object> value);

    /**
     * 写入缓存，并且设置失效时间
     * @param key 缓存键
     * @param value 缓存值
     * @param seconds 时间时间，单位秒
     */
    public abstract void set(String key, Map<String, Object> value, long seconds);

    /**
     * 当键不存在的时候，才写入
     * @param key 缓存键
     * @param value 缓存值
     */
    public abstract void setIfAbsent(String key, Map<String, Object> value);

    /**
     * 当键不存在的时候，才写入，同时设置失效时间
     * @param key 缓存键
     * @param value 缓存值
     * @param seconds 时间时间，单位秒
     */
    public abstract void setIfAbsent(String key, Map<String, Object> value, long seconds);

    /**
     * 设置键的失效时间
     * @param key 缓存键
     * @param seconds 时间时间，单位秒
     */
    public abstract void expires(String key, long seconds);

    /**
     * 获取缓存
     * @param key 键
     * @param t 泛类
     * @param <T> 泛类
     * @return
     */
    public abstract <T> T get(String key, String field, Class<T> t);

    /**
     * 获取缓存
     * @param key 键
     * @return
     */
    public abstract Object get(String key, String field);

    /**
     * 删除缓存
     * @param key
     */
    public abstract void delete(String key);

    /**
     * 判断键是否存在
     * @return
     */
    public abstract boolean isExits(String key);
}
