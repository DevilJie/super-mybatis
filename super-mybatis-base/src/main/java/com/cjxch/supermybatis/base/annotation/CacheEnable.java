package com.cjxch.supermybatis.base.annotation;

import java.lang.annotation.*;

/**
 * dao缓存开关
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/19/17:08
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target( {ElementType.TYPE})
public @interface CacheEnable {

    /**
     * 开启全局缓存，失效时间
     * @return
     */
    long expires() default 0l;
}
