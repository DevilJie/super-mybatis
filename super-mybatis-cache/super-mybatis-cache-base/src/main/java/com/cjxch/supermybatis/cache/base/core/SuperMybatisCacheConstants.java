package com.cjxch.supermybatis.cache.base.core;

import com.cjxch.supermybatis.core.setting.DbCacheSetting;

/**
 * 全局缓存插件常量类
 * @Author: 菜鸡小彩虹
 * @Date: 2020/10/02/19:50
 * @Email: cjxch@cjxch.com
 */
public class SuperMybatisCacheConstants {

    /**
     * 缓存插件
     */
    public static SuperMybatisCache superMybatisCache = null;

    public static DbCacheSetting dbCacheSetting = null;

    public static final String HSET_RESULT = "result";

    public static final String HSET_CLASS = "class";

    public static final String HSET_METHOD = "method";

    public static final String HSET_ARGS = "args";
}
