package com.cjxch.supermybatis.core.datasource;

import java.lang.annotation.*;

/**
 * 注解标签
 * 作用于 方法、接口、类、枚举、注解
 * */

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface ChooseDataSource {
 String value();
}