package com.hsj.supermybatis.core.setting;

import org.springframework.core.env.Environment;

/**
 * Super-Mybatis 常量类
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/19/18:26
 */
public interface GlobalConstants {

    /**
     * 项目名称
     */
    String SUPER_MYBATIS = "super-mybatis";
    /**
     * 雪花id构建器缓存键
     */
    String SNOWFLAKE_GENERATOR_KEY = "SNOWFLAKE_GENERATOR_KEY";

    /**
     * UUID构建器缓存键
     */
    String UUID_GENERATOR_KEY = "UUID_GENERATOR_KEY";



}
