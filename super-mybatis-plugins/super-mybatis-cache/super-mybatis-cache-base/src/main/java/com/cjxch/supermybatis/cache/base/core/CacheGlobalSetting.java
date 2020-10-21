package com.cjxch.supermybatis.cache.base.core;

import com.cjxch.supermybatis.core.setting.GlobalSetting;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/10/21/17:03
 * @Email: cjxch@cjxch.com
 */
public class CacheGlobalSetting extends GlobalSetting {

    private DbCacheSetting dbCacheSetting = new DbCacheSetting();

    public DbCacheSetting getDbCacheSetting() {
        return dbCacheSetting;
    }

    public void setDbCacheSetting(DbCacheSetting dbCacheSetting) {
        this.dbCacheSetting = dbCacheSetting;
    }
}
