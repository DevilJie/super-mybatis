package com.cjxch.supermybatis.core.actuator.simple;

import com.cjxch.supermybatis.core.actuator.SuperMybatisBaseQuery;
import com.cjxch.supermybatis.core.actuator.SuperMybatisBaseSession;
import com.cjxch.supermybatis.core.mapper.BaseMapper;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/30/22:05
 */
public class SuperMybatisSimpleSession extends SuperMybatisBaseSession {

    public SuperMybatisSimpleSession(BaseMapper baseMapper) {
        this.setBaseMapper(baseMapper);
    }

    @Override
    public SuperMybatisBaseQuery createQuery(String sql) {
        return new SuperMybatisSimpleQuery(this.baseMapper, sql);
    }

}
