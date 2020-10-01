package com.cjxch.supermybatis.core.enu;

import com.cjxch.supermybatis.core.provider.GetSqlProvider;

import java.lang.annotation.*;

/**
 * 自定义sql解析器注解
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/22/15:52
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target( {ElementType.TYPE})
public @interface SuperMybatisSqlProvider {

    /**
     * 解析器名称
     * @return
     */
    Class value() default GetSqlProvider.class;
}
