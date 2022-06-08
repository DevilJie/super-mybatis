package com.cjxch.supermybatis.cache.memcached.config;

import com.cjxch.supermybatis.cache.base.autoconfigure.SuperMybatisCacheAutoConfiguration;
import com.cjxch.supermybatis.cache.base.core.SuperMybatisCacheConstants;
import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AutoConfigureAfter(SuperMybatisCacheAutoConfiguration.class)
public class MemcacheConfiguration {


    @Bean
    public SockIOPool sockIOPool () {
        SockIOPool pool = SockIOPool.getInstance();
        pool.setServers(new String[]{SuperMybatisCacheConstants.dbCacheSetting.getMemcachedHost()+":"+ SuperMybatisCacheConstants.dbCacheSetting.getMemcachedPort()});
        pool.setFailover(SuperMybatisCacheConstants.dbCacheSetting.isMemcachedFailover());
        pool.setInitConn(SuperMybatisCacheConstants.dbCacheSetting.getMemcachedInitConn());
        pool.setMinConn(SuperMybatisCacheConstants.dbCacheSetting.getMemcachedMinConn());
        pool.setMaxConn(SuperMybatisCacheConstants.dbCacheSetting.getMemcachedMaxConn());
        pool.setMaintSleep(SuperMybatisCacheConstants.dbCacheSetting.getMemcachedMaintSleep());
        pool.setNagle(SuperMybatisCacheConstants.dbCacheSetting.isMemcachedNagle());
        pool.setSocketTO(SuperMybatisCacheConstants.dbCacheSetting.getMemcachedSockeetTO());
        pool.setAliveCheck(SuperMybatisCacheConstants.dbCacheSetting.isMemcachedAliveCheck());

        pool.initialize();
        return pool;
    }

    @Bean
    public MemCachedClient memCachedClient(){
        return new MemCachedClient();
    }

}