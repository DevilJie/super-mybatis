package com.cjxch.supermybatis.core.actuator;

import com.cjxch.supermybatis.base.bean.Pager;
import com.cjxch.supermybatis.core.mapper.BaseMapper;
import com.cjxch.supermybatis.core.parser.SqlProviderConstants;
import com.cjxch.supermybatis.core.tools.StrUtil;

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

        if (value instanceof Object[] || value instanceof List) {
            String random = StrUtil.RadomCode(36, "other");
            Object[] o;
            if (value instanceof List) {
                o = ((List) value).toArray();
            } else {
                o = (Object[]) value;
            }
            String sql_ = "(";
            String in = "";
            int n = 0;
            Object v_ = "";
            for (Object obj : o) {
                v_ = obj;
                if (obj.getClass().isEnum()) {
                    v_ = processEnum(obj.toString(), obj.getClass());
                }
                n++;
                in += ",#{" + random + "in_" + n + "}";
                parameter.put(random + "in_" + n, v_);
            }
            sql_ += in.substring(1) + ")";
            sql = sql.replace("#{"+key+"}", sql_);
            this.parameter.put(SqlProviderConstants.SQL, sql);
        }
        return this;
    }

    private String processEnum(String v, Class c) {
        String returnStr = "";
        for (int i = 0; i < c.getEnumConstants().length; i++) {
            if (v.equals(c.getEnumConstants()[i].toString())) {
                returnStr = i + "";
                break;
            }
        }
        return returnStr;
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
