package com.cjxch.supermybatis.cache.redis.init;

import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/10/02/20:44
 * @Email: cjxch@cjxch.com
 */
public class SuperMybatisJedisPoolConfig extends JedisPoolConfig {

    public SuperMybatisJedisPoolConfig(){
        setMaxIdle(SuperMybatisRedisConstants.dbCacheSetting.getRedismMxIdle());
        setMaxTotal(SuperMybatisRedisConstants.dbCacheSetting.getRedisMaxTotal());
        setMaxWaitMillis(SuperMybatisRedisConstants.dbCacheSetting.getRedisMaxWaitMillis());
        setTestOnBorrow(true);
        setMaxIdle(SuperMybatisRedisConstants.dbCacheSetting.getRedismMxIdle());
    }
}
