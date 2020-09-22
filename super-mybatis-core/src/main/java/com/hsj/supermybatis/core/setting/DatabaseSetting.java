package com.hsj.supermybatis.core.setting;

import com.hsj.supermybatis.base.enu.PrimaryKeyType;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/19/18:58
 */
public class DatabaseSetting {

    /**
     * 默认数据库表明前缀
     */
    private String tablePerfix = "";

    /**
     * 默认主键生成策略
     */
    private PrimaryKeyType primaryKeyType = PrimaryKeyType.SNOWFLAKE;

    /**
     * 表名是否开启驼峰和下划线分割互转
     * true: 实体类 驼峰 --> 数据库下划线分割
     * false: 实体类 --> 数据库 保持一致
     */
    private Boolean camelModel = true;

    /**
     * 表名和数据库名称是否转为大写
     */
    private Boolean upperCaseMode = false;

    /**
     * 逻辑删除数据库对应值，默认 1 表示删除
     */
    private String logicalDelValue = "1";
    /**
     * 逻辑未删除数据库对应值，默认 0 表示未删除
     */
    private String logicalNotDelValue = "0";

    /**
     * 是否显示执行的sql语句
     */
    public Boolean showSql = false;

    public Boolean getShowSql() {
        return showSql;
    }

    public void setShowSql(Boolean showSql) {
        this.showSql = showSql;
    }

    public String getTablePerfix() {
        return tablePerfix;
    }

    public void setTablePerfix(String tablePerfix) {
        this.tablePerfix = tablePerfix;
    }

    public PrimaryKeyType getPrimaryKeyType() {
        return primaryKeyType;
    }

    public void setPrimaryKeyType(PrimaryKeyType primaryKeyType) {
        this.primaryKeyType = primaryKeyType;
    }

    public Boolean getCamelModel() {
        return camelModel;
    }

    public void setCamelModel(Boolean camelModel) {
        this.camelModel = camelModel;
    }

    public Boolean getUpperCaseMode() {
        return upperCaseMode;
    }

    public void setUpperCaseMode(Boolean upperCaseMode) {
        this.upperCaseMode = upperCaseMode;
    }

    public String getLogicalDelValue() {
        return logicalDelValue;
    }

    public void setLogicalDelValue(String logicalDelValue) {
        this.logicalDelValue = logicalDelValue;
    }

    public String getLogicalNotDelValue() {
        return logicalNotDelValue;
    }

    public void setLogicalNotDelValue(String logicalNotDelValue) {
        this.logicalNotDelValue = logicalNotDelValue;
    }
}
