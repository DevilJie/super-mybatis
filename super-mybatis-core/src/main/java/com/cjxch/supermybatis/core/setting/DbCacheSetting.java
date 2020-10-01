package com.cjxch.supermybatis.core.setting;

import com.cjxch.supermybatis.base.enu.DbCacheType;
import com.cjxch.supermybatis.core.cache.SuperMybatisCache;

/**
 * 数据库缓存配置
 * @Author: 菜鸡小彩虹
 * @Date: 2020/10/01/8:24
 */
public class DbCacheSetting {

    /**
     * 缓存类型
     */
    DbCacheType dbCacheType;

    /**
     * 缓存工具类
     */
    SuperMybatisCache superMybatisCache;

    public DbCacheType getDbCacheType() {
        return dbCacheType;
    }

    public void setDbCacheType(DbCacheType dbCacheType) {
        this.dbCacheType = dbCacheType;
    }

    public SuperMybatisCache getSuperMybatisCache() {
        return superMybatisCache;
    }

    public void setSuperMybatisCache(SuperMybatisCache superMybatisCache) {
        this.superMybatisCache = superMybatisCache;
    }
}
