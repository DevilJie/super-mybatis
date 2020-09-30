package com.hsj.supermybatis.base.enu;

import com.hsj.supermybatis.base.bean.Pager;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 数据库类型，主要针对分页查询进行优化
 * 以下是分页查询 第一页的 示例
 * Mysql：SELECT * FROM TABLE LIMIT 0,10
 * Oracle：SELECT * FROM (SELECT tt.*, ROWNUM AS rowno FROM (  SELECT t.* FROM TABLE t ) tt WHERE ROWNUM <= 10) table_alias WHERE table_alias.rowno >= 0;
 * SqlServer：SELECT * FROM TABLE offset 0 rows fetch next 10 rows only;
 * PostgreSQL: SELECT * FROM TABLE 10 offset 0
 * SQLite: SELECT * FROM TABLE limit 10 offset 0
 * MariaDB: SELECT * FROM TABLE LIMIT 0,10
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/25/17:44
 */
public enum DbType {

    MySql(new String[]{"com.mysql.cj.jdbc.Driver"}, "#sql# LIMIT #pageStart#,#pageSize#"),
    Oracle(new String[]{"oracle.jdbc.driver.OracleDriver"}, "SELECT * FROM (SELECT tt.*, ROWNUM AS rowno FROM (  #sql# ) tt WHERE ROWNUM < #pageEnd#) table_alias WHERE table_alias.rowno >= #pageStart#"),
    SqlServer(new String[]{"com.microsoft.jdbc.sqlserver.SQLServerDriver", "com.microsoft.sqlserver.jdbc.SQLServerDriver"}, "#sql# offset #pageStart# rows fetch next #pageSize# rows only"),
    PostgreSQL(new String[]{"org.postgresql.Driver"}, "#sql# #pageSize# offset #pageStart#"),
    SQLite(new String[]{"org.sqlite.JDBC"}, "#sql# limit #pageSize# offset #pageStart#"),
    MariaDB(new String[]{"org.mariadb.jdbc.Driver"}, "#sql# LIMIT #pageStart#,#pageSize#");

    private String[] driverClass;
    private String sqlTemplate;

    DbType(String[] driverClass, String sqlTemplate) {
        this.driverClass = driverClass;
        this.sqlTemplate = sqlTemplate;
    }

    public String[] getDriverClass() {
        return driverClass;
    }

    public static String getPagerSql(String sql, String driverClass, Pager pager){
        AtomicReference<String> sqlRet = new AtomicReference<>("");
        Arrays.asList(DbType.values()).stream().filter(i -> {
            for(String str : i.getDriverClass()){
                if(str.equals(driverClass)) return true;
            }
            return false;
        }).forEach(i -> {
            sqlRet.set(getPagerSql(sql, i, pager));
            return;
        });

        return sqlRet.get();
    }

    private static String getPagerSql(String sql, DbType dbType, Pager pager){
        sql = dbType.sqlTemplate.replace("#sql#", sql);
        Integer pageSize = pager.getPageSize();
        Integer pageNumber = pager.getPageNumber();
        Integer pageStart = (pageNumber - 1) * pageSize;
        Integer pageEnd = pageStart + pageSize;

        sql = sql.replace("#pageSize#", pageSize.toString()).replace("#pageStart#", pageStart.toString()).replace("#pageEnd#", pageEnd.toString());
        return sql;
    }
}
