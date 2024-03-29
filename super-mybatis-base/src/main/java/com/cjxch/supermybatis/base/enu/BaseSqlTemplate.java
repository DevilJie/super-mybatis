package com.cjxch.supermybatis.base.enu;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/21/7:30
 */
public enum BaseSqlTemplate {

    GET ("SELECT * FROM %s WHERE %s = %s"), //根据主键获取
    DELETE ("DELETE FROM %s WHERE %s = %s"), //根据主键删除
    DELETE_BY_CRITERIA ("DELETE FROM %s %s"), //根据主键删除
    INSERT ("INSERT INTO %s (%s) VALUES (%s)"), //保存
    BATCH_INSERT ("INSERT INTO %s (%s) VALUES \n%s"), //批量保存
    UPDATE ("UPDATE %s %s WHERE %s = %s"), //更新
    ALL_LIST("SELECT * FROM %s"), //获取所有数据
    SELECT("SELECT %s FROM %s %s %s"), //获取所有数据
    SELECT_COUNT("SELECT count(*) FROM %s %s"); //获取所有数据


    private final String sql;

    BaseSqlTemplate(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }
}
