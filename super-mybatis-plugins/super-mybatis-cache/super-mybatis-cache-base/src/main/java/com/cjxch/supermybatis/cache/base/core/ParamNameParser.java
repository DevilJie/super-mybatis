package com.cjxch.supermybatis.cache.base.core;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/10/21/17:49
 * @Email: cjxch@cjxch.com
 */
public class ParamNameParser {

    private static ConcurrentHashMap<String, String[]> map = new ConcurrentHashMap<String, String[]>();

    public static void put(String key, String[] paramNames) {
        map.putIfAbsent(key, paramNames);
    }

    public static String[] get(String key) {
        return map.get(key);
    }

}
