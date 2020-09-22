package com.hsj.supermybatis.core.generator;

import java.util.List;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/19/18:02
 */
public interface IdentifierGenerator {

    /**
     * 生成Id
     *
     * @param entity 实体
     * @return id
     */
    String nextId(Object entity);

    /**
     * 生成Id
     *
     * @param entity 实体
     * @return id
     */
    List<String> nextIds(Object entity, Integer size);
}
