package com.hsj.supermybatis.base.enu;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/21/7:30
 */
public enum BaseSqlTemplate {

    GET ("SELECT * FROM %s WHERE %s = %s"), //根据主键获取
    INSERT ("INSERT INTO %s (%s) VALUES (%s)"), //保存
    BATCH_INSERT ("INSERT INTO %s (%s) VALUES \n%s"); //批量保存


    private final String sql;

    BaseSqlTemplate(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }
}
