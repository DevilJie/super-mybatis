package com.cjxch.supermybatis.base.enu;

/**
 * 数据库缓存工具类型
 * @Author: 菜鸡小彩虹
 * @Date: 2020/10/01/8:24
 */
public enum DbCacheType {
    Redis("com.cjxch.supermybatis.cache.redis.core.SuperMybatisRedisCache"),
    Memcached("com.cjxch.supermybatis.cache.memcached.core.SuperMybatisMemcachedCache"),
    Ehcache("com.cjxch.supermybatis.cache.ehcache.core.SuperMybatisEhcacheCache");

    private String classPath;

    DbCacheType(String classPath){
        this.classPath = classPath;
    }

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }
}