package com.cjxch.supermybatis.cache.base.annotation;

import java.lang.annotation.*;

/**
 * 更新 缓存，注解
 * @Author: 菜鸡小彩虹
 * @Date: 2020/10/01/12:23
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface CacheUpd {

    /**
     * 写入缓存的失效时间
     * @return
     */
    long expires() default 0l;

    /**
     * 写入缓存的key
     */
    String key() default "";
}
