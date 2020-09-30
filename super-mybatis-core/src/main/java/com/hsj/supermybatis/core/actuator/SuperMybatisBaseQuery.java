package com.hsj.supermybatis.core.actuator;

import com.hsj.supermybatis.base.bean.Pager;
import com.hsj.supermybatis.core.mapper.BaseMapper;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class SuperMybatisBaseQuery {

    protected String sql;
    protected Class entityClass;
    private BaseMapper baseMapper;

    public void setSql(String sql) {
        this.sql = sql;
    }

    /**
     * 返回指定类型的列表
     * @param t 泛类型
     * @param <T> 泛类型
     * @return
     */
    public <T> List<T> list(Class<T> t) {
        return null;
    };

    /**
     * 获取列表
     * @return
     */
    public List<HashMap<String, Object>> list(){
        return null;
    }

    /**
     * 返回一个单元的数据
     * 
     * @return
     */
    public Serializable uniqueResult() {
        return null;
    };

    /**
     * 返回指定类型的对象
     * @param t 泛类型
     * @param <T> 泛类型
     * @return
     */
    public <T> T uniqueObject(Class<T> t) {
        return null;
    };

    /**
     * 返回一个对象
     *
     * @return
     */
    public Object uniqueObject(){return null;}

    /**
     * 执行更新sql
     * 
     * @return
     */
    public Long executeUpdate() {
        return null;
    };

    /**
     * 执行sql
     * 
     * @return
     */
    public Serializable execute() {
        return null;
    };

    /**
     * 分页查询
     * 
     * @param pager
     * @return
     */
    public Pager queryPager(Pager pager) {
        return null;
    };

    protected Map<String, Object> parameter = new HashMap<String, Object>();

    public SuperMybatisBaseQuery setParameter(Map<String, Object> parameter) {
        this.parameter.putAll(parameter);
        return this;
    }

    public SuperMybatisBaseQuery setParameter(String key, Object value) {
        this.parameter.put(key, value);
        return this;
    }


    public BaseMapper getBaseMapper() {
        return baseMapper;
    }

    public void setBaseMapper(BaseMapper baseMapper) {
        this.baseMapper = baseMapper;
    }

    public void setEntityClass(Class entityClass) {
        this.entityClass = entityClass;
    }
}
