package com.cjxch.supermybatis.cache.memcached.init;

import com.cjxch.supermybatis.cache.base.core.SuperMybatisCacheConstants;
import com.whalin.MemCached.SockIOPool;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/10/21/19:02
 * @Email: cjxch@cjxch.com
 */
public class SuperMybatisCacheSockIOPool extends SockIOPool {

    public SuperMybatisCacheSockIOPool() {
        setServers(new String[]{SuperMybatisCacheConstants.dbCacheSetting.getMemcachedHost()+":"+ SuperMybatisCacheConstants.dbCacheSetting.getMemcachedPort()});
        setFailover(SuperMybatisCacheConstants.dbCacheSetting.isMemcachedFailover());
        setInitConn(SuperMybatisCacheConstants.dbCacheSetting.getMemcachedInitConn());
        setMinConn(SuperMybatisCacheConstants.dbCacheSetting.getMemcachedMinConn());
        setMaxConn(SuperMybatisCacheConstants.dbCacheSetting.getMemcachedMaxConn());
        setMaintSleep(SuperMybatisCacheConstants.dbCacheSetting.getMemcachedMaintSleep());
        setNagle(SuperMybatisCacheConstants.dbCacheSetting.isMemcachedNagle());
        setSocketTO(SuperMybatisCacheConstants.dbCacheSetting.getMemcachedSockeetTO());
        setAliveCheck(SuperMybatisCacheConstants.dbCacheSetting.isMemcachedAliveCheck());
    }
}
