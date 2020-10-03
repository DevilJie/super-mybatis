package com.cjxch.supermybatis.cache.base.log;

import org.slf4j.Logger;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/10/02/21:20
 * @Email: cjxch@cjxch.com
 */
public class PrintLog {

    private static final String PERFIX = "【Super-Mybatis-Cache】";

    public static void DEBUG(Logger logger, String content){
        logger.debug(PERFIX + content);
    }
}
