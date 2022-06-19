package com.cjxch.supermybatis.cache.base.core;

import com.cjxch.supermybatis.base.enu.DbCacheType;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/10/02/19:27
 * @Email: cjxch@cjxch.com
 */
public class DbCacheSetting {

    /*********************全局设置**************************/
    /**
     * 缓存开关，默认打开
     */
    private Boolean cacheSwitch = true;


    /*************************redis配置*******************************/

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

    //最大空闲线程
    private Integer redisMaxIdle = 10;

    //最小空闲线程
    private Integer redisMinIdle = 5;

    //最大线程数
    private Integer redisMaxTotal = 10;

    //当池内没有可用的连接时，最大等待时间（单位 秒）
    private Long redisMaxWaitMillis = 10000l;

    //链接的Redis数据库
    private Integer redisDatabase = 0;



    /*************************memcache配置*******************************/
    /**
     * memcached
     */
    private String memcachedHost = "127.0.0.1";

    /**
     * 默认缓存10分钟
     */
    private Long memcachedExpires = 60 * 10l;

    /**
     * memcached端口
     */
    private int memcachedPort = 11211;

    private boolean memcachedFailover = true;

    private int memcachedInitConn = 10;

    private int memcachedMaxConn = 100;

    private int memcachedMinConn = 20;

    private int memcachedMaintSleep = 50;

    private boolean memcachedNagle = false;

    private int memcachedSockeetTO = 3000;

    private boolean memcachedAliveCheck = true;


    /****************************ehcache相关配置***************************/

    /**
     * 缓存所能使用的堆内存的最大字节数的，其单位可以是K、M或G，不区分大小写
     */
    private String maxBytesLocalHeap = "500M";

    /**
     * 用来限制缓存所能使用的非堆内存的最大字节数，其单位也可以是K、M或G.
     */
    private String maxBytesLocalOffHeap = "2G";

    /**
     * 用来限制缓存所能使用的磁盘的最大字节数的，其单位可以是K、M或G。
     */
    private String maxBytesLocalDisk = "50G";

    public String getMaxBytesLocalDisk() {
        return maxBytesLocalDisk;
    }

    public void setMaxBytesLocalDisk(String maxBytesLocalDisk) {
        this.maxBytesLocalDisk = maxBytesLocalDisk;
    }

    public String getMaxBytesLocalHeap() {
        return maxBytesLocalHeap;
    }

    public void setMaxBytesLocalHeap(String maxBytesLocalHeap) {
        this.maxBytesLocalHeap = maxBytesLocalHeap;
    }

    public String getMaxBytesLocalOffHeap() {
        return maxBytesLocalOffHeap;
    }

    public void setMaxBytesLocalOffHeap(String maxBytesLocalOffHeap) {
        this.maxBytesLocalOffHeap = maxBytesLocalOffHeap;
    }

    public boolean isMemcachedFailover() {
        return memcachedFailover;
    }

    public int getMemcachedMinConn() {
        return memcachedMinConn;
    }

    public void setMemcachedMinConn(int memcachedMinConn) {
        this.memcachedMinConn = memcachedMinConn;
    }

    public void setMemcachedFailover(boolean memcachedFailover) {
        this.memcachedFailover = memcachedFailover;
    }

    public int getMemcachedInitConn() {
        return memcachedInitConn;
    }

    public void setMemcachedInitConn(int memcachedInitConn) {
        this.memcachedInitConn = memcachedInitConn;
    }

    public int getMemcachedMaxConn() {
        return memcachedMaxConn;
    }

    public void setMemcachedMaxConn(int memcachedMaxConn) {
        this.memcachedMaxConn = memcachedMaxConn;
    }

    public int getMemcachedMaintSleep() {
        return memcachedMaintSleep;
    }

    public void setMemcachedMaintSleep(int memcachedMaintSleep) {
        this.memcachedMaintSleep = memcachedMaintSleep;
    }

    public boolean isMemcachedNagle() {
        return memcachedNagle;
    }

    public void setMemcachedNagle(boolean memcachedNagle) {
        this.memcachedNagle = memcachedNagle;
    }

    public int getMemcachedSockeetTO() {
        return memcachedSockeetTO;
    }

    public void setMemcachedSockeetTO(int memcachedSockeetTO) {
        this.memcachedSockeetTO = memcachedSockeetTO;
    }

    public boolean isMemcachedAliveCheck() {
        return memcachedAliveCheck;
    }

    public void setMemcachedAliveCheck(boolean memcachedAliveCheck) {
        this.memcachedAliveCheck = memcachedAliveCheck;
    }

    public String getMemcachedHost() {
        return memcachedHost;
    }

    public void setMemcachedHost(String memcachedHost) {
        this.memcachedHost = memcachedHost;
    }

    public Long getMemcachedExpires() {
        return memcachedExpires;
    }

    public void setMemcachedExpires(Long memcachedExpires) {
        this.memcachedExpires = memcachedExpires;
    }

    public int getMemcachedPort() {
        return memcachedPort;
    }

    public void setMemcachedPort(int memcachedPort) {
        this.memcachedPort = memcachedPort;
    }

    /**
     * 缓存类型
     */
    private DbCacheType dbCacheType = null;

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

    public Integer getRedisMaxIdle() {
        return redisMaxIdle;
    }

    public void setRedisMaxIdle(Integer redisMaxIdle) {
        this.redisMaxIdle = redisMaxIdle;
    }

    public Integer getRedisMinIdle() {
        return redisMinIdle;
    }

    public void setRedisMinIdle(Integer redisMinIdle) {
        this.redisMinIdle = redisMinIdle;
    }

    public Integer getRedisMaxTotal() {
        return redisMaxTotal;
    }

    public void setRedisMaxTotal(Integer redisMaxTotal) {
        this.redisMaxTotal = redisMaxTotal;
    }

    public Long getRedisMaxWaitMillis() {
        return redisMaxWaitMillis;
    }

    public void setRedisMaxWaitMillis(Long redisMaxWaitMillis) {
        this.redisMaxWaitMillis = redisMaxWaitMillis;
    }

    public Long getExpires() {
        return expires;
    }

    public void setExpires(Long expires) {
        this.expires = expires;
    }

    public Boolean getCacheSwitch() { return cacheSwitch; }

    public void setCacheSwitch(Boolean cacheSwitch) { this.cacheSwitch = cacheSwitch; }

    public Integer getRedisDatabase() {
        return redisDatabase;
    }

    public void setRedisDatabase(Integer redisDatabase) {
        this.redisDatabase = redisDatabase;
    }
}
