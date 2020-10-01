/*
 * Copyright 2020-2030, cjxch (cjxch@cjxch.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 *     http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cjxch.supermybatis.base.annotation;

import com.cjxch.supermybatis.base.enu.MatchMode;

import java.lang.annotation.*;

/**
 * 数据表字段标识
 *
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/19/17:08
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface Column {
    /**
     * 当执行update语句的时候，该字段拼接set语句的策略
     * true：update table set column=#{column} 无论值是否为空，都会赋值
     * false：自动识别字段类型
     *        数字型：update table set  <if test="column != null">column=#{column}</if>
     *        字符型：update table set  <if test="column != null and column != ''">column=#{column}</if>
     *
     * 如果实体类不设置这个注解，则随着全局设置走
     * @return
     */
    boolean updateAnyway() default false;

    /**
     * 是否为逻辑删除字段
     * true：如果开启true，则表示此字段作为逻辑删除字段，数据库存储的值为 0 = 未删除 1 = 已删除，
     *       在执行删除语句的时候，只会将此字段更新为 1，表示数据被删除
     * false：关闭逻辑删除
     * @return
     */
    boolean logicalDelFlag() default false;

    /**
     * 设置数据表字段的名称
     * 比如实体类属性是 subTitle，数据库字段为wx_sub_title
     * 如果要做到自动映射，设置此属性为  wx_sub_title 即可
     * 一般情况下不需要设置，直接采用框架自动映射策略即可
     * 一旦设置了此处的名称，将会强制对应
     * 设置开启的驼峰下换线转换功能将会失效
     * @return
     */
    String name() default "";

    /**
     * 拼接查询语句时，该字段的匹配模式
     * @return
     */
    MatchMode matchMode() default MatchMode.FULL_MATCH;

    /**
     * 匹配模式作用字段
     * 与匹配模式配合使用
     * 使用场景：需要查询某个时间段注册的用户，就需要2个属性值作为起始值和终止值，设置属性的作用字段就可以解决
     * @return
     */
    String matchBase() default "";

    /**
     * 忽略此字段
     * 在拼接sql的时候，不拼接此字段
     * @return
     */
    boolean ignored() default false;
}
