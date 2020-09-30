package com.hsj.supermybatis.core.provider;

import com.hsj.supermybatis.core.parser.BaseSqlProviderParser;
import java.util.Map;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/21/7:29
 */
@Deprecated
public class BaseSqlProvider {

    public BaseSqlProvider(){}

    /**
     * 拼接SQL的表名称
     */
    protected String TABLE_NAME;

    /**
     * 拼接SQL的表别名
     */
    protected String TABLE_NAME_ALIAS;

    /**
     * 拼接联查SQL的所有表名称
     */
    protected String[] JOIN_TABLE_NAME;

    /**
     * 拼接联查SQL的所有表别名
     */
    protected String[] JOIN_TABLE_NAME_ALIAS;

    /**
     * 主键列名
     */
    protected String PRIMARY_KEY;

    public String execute(Map<String, Object> map){
        BaseSqlProviderParser parser = BaseSqlProviderParser.cachedParser.get(this.getClass().getName());
        parser.execute(map);
        return null;
    }

    public BaseSqlProvider setTABLE_NAME(String TABLE_NAME) {
        this.TABLE_NAME = TABLE_NAME;
        return this;
    }

    public BaseSqlProvider setTABLE_NAME_ALIAS(String TABLE_NAME_ALIAS) {
        this.TABLE_NAME_ALIAS = TABLE_NAME_ALIAS;
        return this;
    }

    public BaseSqlProvider setJOIN_TABLE_NAME(String[] JOIN_TABLE_NAME) {
        this.JOIN_TABLE_NAME = JOIN_TABLE_NAME;
        return this;
    }

    public BaseSqlProvider setJOIN_TABLE_NAME_ALIAS(String[] JOIN_TABLE_NAME_ALIAS) {
        this.JOIN_TABLE_NAME_ALIAS = JOIN_TABLE_NAME_ALIAS;
        return this;
    }

    public BaseSqlProvider setPRIMARY_KEY(String PRIMARY_KEY) {
        this.PRIMARY_KEY = PRIMARY_KEY;
        return this;
    }
}
