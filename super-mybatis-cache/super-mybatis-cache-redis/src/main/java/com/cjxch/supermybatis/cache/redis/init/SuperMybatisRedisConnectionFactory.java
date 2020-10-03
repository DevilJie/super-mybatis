package com.cjxch.supermybatis.cache.redis.init;

import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/10/02/20:47
 * @Email: cjxch@cjxch.com
 */
public class SuperMybatisRedisConnectionFactory extends JedisConnectionFactory {

    public SuperMybatisRedisConnectionFactory(){
        setHostName(SuperMybatisRedisConstants.dbCacheSetting.getRedisHost());
        setPort(SuperMybatisRedisConstants.dbCacheSetting.getRedisPort());
        setPassword(SuperMybatisRedisConstants.dbCacheSetting.getRedisPwd());
        setTimeout(SuperMybatisRedisConstants.dbCacheSetting.getRedisTimeout());
    }
}
