package com.cjxch.supermybatis.cache.redis.init;

import com.cjxch.supermybatis.cache.base.core.SuperMybatisCacheConstants;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/10/02/20:44
 * @Email: cjxch@cjxch.com
 */
public class SuperMybatisJedisPoolConfig extends JedisPoolConfig {

    public SuperMybatisJedisPoolConfig(){
        setMaxIdle(SuperMybatisCacheConstants.dbCacheSetting.getRedismMxIdle());
        setMaxTotal(SuperMybatisCacheConstants.dbCacheSetting.getRedisMaxTotal());
        setMaxWaitMillis(SuperMybatisCacheConstants.dbCacheSetting.getRedisMaxWaitMillis());
        setTestOnBorrow(true);
        setMaxIdle(SuperMybatisCacheConstants.dbCacheSetting.getRedismMxIdle());
    }
}
