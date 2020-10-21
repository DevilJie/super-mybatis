package com.cjxch.supermybatis.cache.base.annotation;

import java.lang.annotation.*;

/**
 * 删除缓存，注解
 * @Author: 菜鸡小彩虹
 * @Date: 2020/10/01/12:23
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface CacheEvict {

    /**
     * 写入缓存的key
     */
    String key() default "";

    String group() default "";
}
