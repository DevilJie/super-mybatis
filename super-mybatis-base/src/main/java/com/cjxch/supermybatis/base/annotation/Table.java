package com.cjxch.supermybatis.base.annotation;

import java.lang.annotation.*;

/**
 * 数据表注解
 *
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/19/17:08
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target( {ElementType.TYPE})
public @interface Table {

    /**
     * 指定对应数据库的表名称
     * 不指定将会自动生成
     * @return
     */
    String value() default "";

}
