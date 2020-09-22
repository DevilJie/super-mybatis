package com.hsj.supermybatis.base.annotation;


import com.hsj.supermybatis.base.enu.PrimaryKeyType;

import java.lang.annotation.*;


/**
 * 主键注解
 *
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/19/17:08
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target( {ElementType.FIELD})
public @interface PrimaryKey {
	/**
	 * 主键列名
	 * @return
	 */
	String key() default "";

	/**
	 * 主键策略
	 * 默认雪花算法
	 * @return
	 */
	PrimaryKeyType keyType() default PrimaryKeyType.SNOWFLAKE;
}
