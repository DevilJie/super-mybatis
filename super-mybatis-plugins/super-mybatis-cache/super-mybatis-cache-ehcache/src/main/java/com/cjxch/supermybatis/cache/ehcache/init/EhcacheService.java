package com.cjxch.supermybatis.cache.ehcache.init;

import com.cjxch.supermybatis.cache.base.core.SuperMybatisCacheConstants;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/10/22/14:04
 * @Email: cjxch@cjxch.com
 */
@Component
public class EhcacheService {

    private final String EHCACHE_PERFIX = "super-mybatis-ehcache-";

    private CacheManager cm = new CacheManager();

    public void set(String key, Object val){
        set(key, val, 0l);
    }

    public void set(String key, Object val, long expires){
        Element e = new Element(key, val);
        getCache(expires).put(e);
    }

    public Object get(String key){
        List<String> names = Arrays.asList(cm.getCacheNames()).stream().filter(i -> cm.getCache(i).get(key) != null).collect(Collectors.toList());
        if(names == null || names.size() == 0) return null;
        return cm.getCache(names.get(0)).get(key).getObjectValue();
    }

    public <T> T get(String key, T t){
        return (T) get(key);
    }

    public void remove(String key){
        Arrays.asList(cm.getCacheNames()).forEach(i -> {
            Ehcache cache = cm.getCache(i);
            cache.remove(key);
        });
    }

    public boolean keyExits(String key){
        return Arrays.asList(cm.getCacheNames()).stream().filter(i -> cm.getCache(i).get(key) != null).collect(Collectors.toList()).size() > 0;
    }

    /**
     * 失效时间
     * @param expire
     * @return
     */
    private Ehcache getCache(long expire){
        Ehcache cache = cm.getCache(EHCACHE_PERFIX + expire);
        if(cache != null) return cache;
        CacheConfiguration configuration = new CacheConfiguration();
        configuration.setTimeToIdleSeconds(0);
        configuration.setTimeToLiveSeconds(expire);
        configuration.setMaxBytesLocalHeap(SuperMybatisCacheConstants.dbCacheSetting.getMaxBytesLocalHeap());
        configuration.setMaxBytesLocalOffHeap(SuperMybatisCacheConstants.dbCacheSetting.getMaxBytesLocalOffHeap());
        configuration.setMaxBytesLocalDisk(SuperMybatisCacheConstants.dbCacheSetting.getMaxBytesLocalDisk());
        configuration.setOverflowToOffHeap(false);
        configuration.setName(EHCACHE_PERFIX + expire);
        cache = new Cache(configuration);
        cache.setName(EHCACHE_PERFIX + expire);
        cm.addCache(cache);
        return cache;
    }
}
