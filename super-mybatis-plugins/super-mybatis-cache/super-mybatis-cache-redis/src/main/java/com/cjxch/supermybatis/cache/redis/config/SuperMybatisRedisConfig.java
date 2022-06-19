package com.cjxch.supermybatis.cache.redis.config;

import com.cjxch.supermybatis.cache.base.autoconfigure.SuperMybatisCacheAutoConfiguration;
import com.cjxch.supermybatis.cache.base.core.SuperMybatisCacheConstants;
import com.cjxch.supermybatis.cache.redis.init.SuperMybatisRedisService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;


@AutoConfigureAfter(SuperMybatisCacheAutoConfiguration.class)
public class SuperMybatisRedisConfig {

    @Bean("supermybatis-jedisPoolConfig")
    public JedisPoolConfig jedisPoolConfig(){
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxIdle(SuperMybatisCacheConstants.dbCacheSetting.getRedisMaxIdle());
            config.setMaxTotal(SuperMybatisCacheConstants.dbCacheSetting.getRedisMaxTotal());
            config.setMaxWait(Duration.ofSeconds(SuperMybatisCacheConstants.dbCacheSetting.getRedisMaxWaitMillis()/1000));
            config.setMinIdle(SuperMybatisCacheConstants.dbCacheSetting.getRedisMinIdle());
            return config;
        }catch(Exception e){
            return null;
        }
    }

    @Bean("supermybatis-jedisConnectionFactory")
    public JedisConnectionFactory jedisConnectionFactory(@Qualifier("supermybatis-jedisPoolConfig") JedisPoolConfig jedisPoolConfig){
        try {
            //单机版jedis
            RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
            //设置redis服务器的host或者ip地址
            redisStandaloneConfiguration.setHostName(SuperMybatisCacheConstants.dbCacheSetting.getRedisHost());
            //设置默认使用的数据库
            redisStandaloneConfiguration.setDatabase(SuperMybatisCacheConstants.dbCacheSetting.getRedisDatabase());
            //设置密码
            redisStandaloneConfiguration.setPassword(RedisPassword.of(SuperMybatisCacheConstants.dbCacheSetting.getRedisPwd()));
            //设置redis的服务的端口号
            redisStandaloneConfiguration.setPort(SuperMybatisCacheConstants.dbCacheSetting.getRedisPort());
            //获得默认的连接池构造器(怎么设计的，为什么不抽象出单独类，供用户使用呢)
            JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpcb = (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder)JedisClientConfiguration.builder();
            //指定jedisPoolConifig
            jpcb.poolConfig(jedisPoolConfig);
            //通过构造器来构造jedis客户端配置
            JedisClientConfiguration jedisClientConfiguration = jpcb.build();
            //单机配置 + 客户端配置 = jedis连接工厂
            return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
        }catch(Exception e){
            return null;
        }
    }

    @Bean("supermybatis-redisTemplate")
    public RedisTemplate redisTemplate(@Qualifier("supermybatis-jedisConnectionFactory") JedisConnectionFactory jedisConnectionFactory){
        try{
            if(jedisConnectionFactory == null) return null;
            RedisTemplate temp = new RedisTemplate();
            StringRedisSerializer ser = new StringRedisSerializer();
            temp.setConnectionFactory(jedisConnectionFactory);
            temp.setDefaultSerializer(ser);
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
