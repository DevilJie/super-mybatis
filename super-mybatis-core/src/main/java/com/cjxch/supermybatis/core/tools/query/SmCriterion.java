package com.cjxch.supermybatis.core.tools.query;

import java.io.Serializable;

/**
 * @author Caizongyou
 */
public class SmCriterion {

    /**
     * 查询键
     */
    private String             key;

    /**
     * 查询值
     */
    private Object             value;

    /**
     * 查询类型
     */
    private CriteriaType type;

    /**
     * 连接符
     */
    private CriteriaConnector connector;

    private SmCriterion(){
    }

    protected SmCriterion(String key, Object value, CriteriaType type,CriteriaConnector connector){
        super();
        this.key = key;
        this.value = value;
        this.type = type;
        this.connector = connector;
    }

    protected SmCriterion(String key, Object value, CriteriaType type){
        super();
        this.key = key;
        this.value = value;
        this.type = type;
    }

    /**
     * 相等
     * 
     * @param key
     * @param value
     * @return
     */
    public static SmCriterion eq(String key, Object value, CriteriaConnector connector) {
        return new SmCriterion(key, value, CriteriaType.eq, connector);
    }

    /**
     * 大于等于
     * 
     * @param key
     * @param value
     * @return
     */
    public static SmCriterion ge(String key, Object value, CriteriaConnector connector) {
        return new SmCriterion(key, value, CriteriaType.ge, connector);
    }

    /**
     * 小于等于
     * 
     * @param key
     * @param value
     * @return
     */
    public static SmCriterion le(String key, Object value, CriteriaConnector connector) {
        return new SmCriterion(key, value, CriteriaType.le, connector);
    }

    /**
     * 大于
     * 
     * @param key
     * @param value
     * @return
     */
    public static SmCriterion gt(String key, Object value, CriteriaConnector connector) {
        return new SmCriterion(key, value, CriteriaType.gt, connector);
    }

    /**
     * 小于
     * 
     * @param key
     * @param value
     * @return
     */
    public static SmCriterion lt(String key, Object value, CriteriaConnector connector) {
        return new SmCriterion(key, value, CriteriaType.lt, connector);
    }

    /**
     * 模糊匹配
     * 
     * @param key
     * @param value
     * @return
     */
    public static SmCriterion like(String key, Object value, CriteriaConnector connector) {
        return new SmCriterion(key, value, CriteriaType.like, connector);
    }

    /**
     * 为空
     * 
     * @param key
     * @return
     */
    public static SmCriterion isNull(String key, CriteriaConnector connector) {
        return new SmCriterion(key, null, CriteriaType.isNull, connector);
    }

    /**
     * 不为空
     * 
     * @param key
     * @return
     */
    public static SmCriterion isNotNull(String key, CriteriaConnector connector) {
        return new SmCriterion(key, null, CriteriaType.isNotNull, connector);
    }

    /**
     * in
     * 
     * @param key
     * @param value
     * @return
     */
    public static SmCriterion in(String key, Object value, CriteriaConnector connector) {
        return new SmCriterion(key, value, CriteriaType.in, connector);
    }


    /**
     * 相等
     *
     * @param key
     * @param value
     * @return
     */
    public static SmCriterion eq(String key, Object value) {
        return new SmCriterion(key, value, CriteriaType.eq);
    }

    /**
     * 大于等于
     *
     * @param key
     * @param value
     * @return
     */
    public static SmCriterion ge(String key, Object value) {
        return new SmCriterion(key, value, CriteriaType.ge);
    }

    /**
     * 小于等于
     *
     * @param key
     * @param value
     * @return
     */
    public static SmCriterion le(String key, Object value) {
        return new SmCriterion(key, value, CriteriaType.le);
    }

    /**
     * 大于
     *
     * @param key
     * @param value
     * @return
     */
    public static SmCriterion gt(String key, Object value) {
        return new SmCriterion(key, value, CriteriaType.gt);
    }

    /**
     * 小于
     *
     * @param key
     * @param value
     * @return
     */
    public static SmCriterion lt(String key, Object value) {
        return new SmCriterion(key, value, CriteriaType.lt);
    }

    /**
     * 模糊匹配
     *
     * @param key
     * @param value
     * @return
     */
    public static SmCriterion like(String key, Object value) {
        return new SmCriterion(key, value, CriteriaType.like);
    }

    /**
     * 为空
     *
     * @param key
     * @return
     */
    public static SmCriterion isNull(String key) {
        return new SmCriterion(key, null, CriteriaType.isNull);
    }

    /**
     * 不为空
     *
     * @param key
     * @return
     */
    public static SmCriterion isNotNull(String key) {
        return new SmCriterion(key, null, CriteriaType.isNotNull);
    }

    /**
     * in
     *
     * @param key
     * @param value
     * @return
     */
    public static SmCriterion in(String key, Object value) {
        return new SmCriterion(key, value, CriteriaType.in);
    }



    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    public CriteriaType getType() { return type; }

    public CriteriaConnector getConnector() { return connector; }
}
