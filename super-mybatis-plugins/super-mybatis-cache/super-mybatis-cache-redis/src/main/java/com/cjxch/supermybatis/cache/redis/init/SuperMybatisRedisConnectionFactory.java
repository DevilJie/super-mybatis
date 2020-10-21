package com.cjxch.supermybatis.cache.redis.init;

import com.cjxch.supermybatis.cache.base.core.SuperMybatisCacheConstants;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/10/02/20:47
 * @Email: cjxch@cjxch.com
 */
public class SuperMybatisRedisConnectionFactory extends JedisConnectionFactory {

    public SuperMybatisRedisConnectionFactory(){
        setHostName(SuperMybatisCacheConstants.dbCacheSetting.getRedisHost());
        setPort(SuperMybatisCacheConstants.dbCacheSetting.getRedisPort());
        setPassword(SuperMybatisCacheConstants.dbCacheSetting.getRedisPwd());
        setTimeout(SuperMybatisCacheConstants.dbCacheSetting.getRedisTimeout());
    }
}
