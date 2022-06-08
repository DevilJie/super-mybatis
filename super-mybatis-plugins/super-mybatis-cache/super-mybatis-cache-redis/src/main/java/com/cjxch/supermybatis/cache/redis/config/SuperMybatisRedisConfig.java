package com.cjxch.supermybatis.cache.redis.config;

import com.cjxch.supermybatis.cache.base.autoconfigure.SuperMybatisCacheAutoConfiguration;
import com.cjxch.supermybatis.cache.base.core.SuperMybatisCacheConstants;
import com.cjxch.supermybatis.cache.redis.init.SuperMybatisRedisService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;


@AutoConfigureAfter(SuperMybatisCacheAutoConfiguration.class)
public class SuperMybatisRedisConfig {

    @Bean("supermybatis-jedisPoolConfig")
    public JedisPoolConfig jedisPoolConfig(){
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxIdle(SuperMybatisCacheConstants.dbCacheSetting.getRedismMxIdle());
            config.setMaxTotal(SuperMybatisCacheConstants.dbCacheSetting.getRedisMaxTotal());
            config.setMaxWaitMillis(SuperMybatisCacheConstants.dbCacheSetting.getRedisMaxWaitMillis());
            config.setTestOnBorrow(true);
            config.setMaxIdle(SuperMybatisCacheConstants.dbCacheSetting.getRedismMxIdle());
            return config;
        }catch(Exception e){
            return null;
        }
    }

    @Bean("supermybatis-jedisConnectionFactory")
    public JedisConnectionFactory jedisConnectionFactory(){
        try {
            JedisConnectionFactory factory = new JedisConnectionFactory();
            factory.setHostName(SuperMybatisCacheConstants.dbCacheSetting.getRedisHost());
            factory.setPort(SuperMybatisCacheConstants.dbCacheSetting.getRedisPort());
            factory.setPassword(SuperMybatisCacheConstants.dbCacheSetting.getRedisPwd());
            factory.setTimeout(SuperMybatisCacheConstants.dbCacheSetting.getRedisTimeout());
            return factory;
        }catch(Exception e){
            return null;
        }
    }

    @Bean("supermybatis-redisTemplate")
    public RedisTemplate redisTemplate(@Qualifier("supermybatis-jedisConnectionFactory") JedisConnectionFactory jedisConnectionFactory){
        try{
            if(jedisConnectionFactory == null) return null;
            RedisTemplate temp = new RedisTemplate();
            temp.setConnectionFactory(jedisConnectionFactory);
            temp.setKeySerializer(new StringRedisSerializer());
            temp.setHashKeySerializer(new StringRedisSerializer());
            temp.setHashValueSerializer(new StringRedisSerializer());
            temp.setEnableTransactionSupport(true);
            return temp;
        }catch(Exception e){
            return null;
        }
    }

    @Bean
    public SuperMybatisRedisService superMybatisRedisService(RedisTemplate redisTemplate){
        SuperMybatisRedisService service = new SuperMybatisRedisService();
        service.setRedisTemplate(redisTemplate);
        return service;
    }
}
