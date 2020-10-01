package com.hsj.supermybatis.core.actuator;

import com.hsj.supermybatis.base.bean.Pager;
import com.hsj.supermybatis.core.mapper.BaseMapper;
import com.hsj.supermybatis.core.parser.SqlProviderConstants;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class SuperMybatisBaseQuery {

    protected String sql;
    protected Class entityClass;
    private BaseMapper baseMapper;

    public void setSql(String sql){
        this.sql = sql;
        this.parameter.put(SqlProviderConstants.SQL, sql);
    }

    /**
     * 返回指定类型的列表
     * @param t 泛类型
     * @param <T> 泛类型
     * @return
     */
    public abstract <T> List<T> list(Class<T> t);

    /**
     * 获取列表
     * @return
     */
    public abstract  List<HashMap<String, Object>> list();

    /**
     * 返回一个单元的数据
     * 
     * @return
     */
    public abstract Serializable uniqueResult();

    /**
     * 返回指定类型的对象
     * @param t 泛类型
     * @param <T> 泛类型
     * @return
     */
    public abstract <T> T uniqueObject(Class<T> t) ;

    /**
     * 返回一个对象
     *
     * @return
     */
    public abstract Object uniqueObject();

    /**
     * 执行更新sql
     * 
     * @return
     */
    public abstract Long executeUpdate();

    /**
     * 执行sql
     * 
     * @return
     */
    public abstract Serializable execute();

    /**
     * 分页查询
     *
     * @param pager
     * @return
     */
    public abstract Pager queryPager(Pager pager);

    /**
     * 分页查询
     *
     * @param pager
     * @return
     */
    public abstract Pager queryPager(Pager pager, Class t);

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
