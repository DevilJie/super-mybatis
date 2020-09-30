package com.hsj.supermybatis.core.actuator;


import com.hsj.supermybatis.core.mapper.BaseMapper;

public abstract class SuperMybatisBaseSession {

    protected BaseMapper baseMapper;

    public void setBaseMapper(BaseMapper baseMapper) {
        this.baseMapper = baseMapper;
    }

    /**
     * 创建查询类
     * 
     * @param sql 执行sql
     * @return
     */
    public abstract SuperMybatisBaseQuery createQuery(String sql);
}
