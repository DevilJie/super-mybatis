package com.cjxch.supermybatis.core.cache;

/**
 * 缓存处理类
 * @Author: 菜鸡小彩虹
 * @Date: 2020/10/01/13:01
 */
public abstract class SuperMybatisCache {

    /**
     * 写入缓存
     * @param key 缓存键
     * @param value 缓存值
     */
    abstract void set(String key, Object value);

    /**
     * 写入缓存，并且设置失效时间
     * @param key 缓存键
     * @param value 缓存值
     * @param seconds 时间时间，单位秒
     */
    abstract void set(String key, Object value, long seconds);

    /**
     * 当键不存在的时候，才写入
     * @param key 缓存键
     * @param value 缓存值
     */
    abstract void setIfAbsent(String key, Object value);

    /**
     * 当键不存在的时候，才写入，同时设置失效时间
     * @param key 缓存键
     * @param value 缓存值
     * @param seconds 时间时间，单位秒
     */
    abstract void setIfAbsent(String key, Object value, long seconds);

    /**
     * 设置键的失效时间
     * @param key 缓存键
     * @param seconds 时间时间，单位秒
     */
    abstract void expires(String key, long seconds);

    /**
     * 获取缓存
     * @param key 键
     * @param t 泛类
     * @param <T> 泛类
     * @return
     */
    abstract <T> T get(String key, Class<T> t);

    /**
     * 删除缓存
     * @param key
     */
    abstract void delete(String key);

    /**
     * 判断键是否存在
     * @return
     */
    abstract boolean isExits(String key);
}
