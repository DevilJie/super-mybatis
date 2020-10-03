package com.cjxch.supermybatis.core.setting;

import com.cjxch.supermybatis.base.enu.DbCacheType;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/10/02/19:27
 * @Email: cjxch@cjxch.com
 */
public class DbCacheSetting {
    /**
     * 数据库缓存开关
     */
    private boolean cacheSwitch = false;

    /**
     * redis的主机ip
     */
    private String redisHost = "127.0.0.1";

    /**
     * 默认缓存10分钟
     */
    private Long expires = 60 * 10l;

    /**
     * redis端口
     */
    private int redisPort = 6379;

    /**
     * redis链接密码
     */
    private String redisPwd;

    private Integer redismMxIdle = 5;

    private Integer redisMaxTotal = 10;

    private Integer redisMaxWaitMillis = 2000;

    private Integer redisTimeout = 0;

    /**
     * 缓存类型
     */
    private DbCacheType dbCacheType;


    public boolean isCacheSwitch() {
        return cacheSwitch;
    }

    public void setCacheSwitch(boolean cacheSwitch) {
        this.cacheSwitch = cacheSwitch;
    }

    public String getRedisHost() {
        return redisHost;
    }

    public void setRedisHost(String redisHost) {
        this.redisHost = redisHost;
    }

    public int getRedisPort() {
        return redisPort;
    }

    public void setRedisPort(int redisPort) {
        this.redisPort = redisPort;
    }

    public String getRedisPwd() {
        return redisPwd;
    }

    public void setRedisPwd(String redisPwd) {
        this.redisPwd = redisPwd;
    }

    public DbCacheType getDbCacheType() {
        return dbCacheType;
    }

    public void setDbCacheType(DbCacheType dbCacheType) {
        this.dbCacheType = dbCacheType;
    }

    public Integer getRedismMxIdle() {
        return redismMxIdle;
    }

    public void setRedismMxIdle(Integer redismMxIdle) {
        this.redismMxIdle = redismMxIdle;
    }

    public Integer getRedisMaxTotal() {
        return redisMaxTotal;
    }

    public void setRedisMaxTotal(Integer redisMaxTotal) {
        this.redisMaxTotal = redisMaxTotal;
    }

    public Integer getRedisMaxWaitMillis() {
        return redisMaxWaitMillis;
    }

    public void setRedisMaxWaitMillis(Integer redisMaxWaitMillis) {
        this.redisMaxWaitMillis = redisMaxWaitMillis;
    }

    public Integer getRedisTimeout() {
        return redisTimeout;
    }

    public void setRedisTimeout(Integer redisTimeout) {
        this.redisTimeout = redisTimeout;
    }

    public Long getExpires() {
        return expires;
    }

    public void setExpires(Long expires) {
        this.expires = expires;
    }
}
