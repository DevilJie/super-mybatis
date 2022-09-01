package com.cjxch.supermybatis.tenant;

/**
 * 多租户信息
 */
public interface SuperMybatisTenantInfo {

    /**
     * 对应数据库列
     * @return
     */
    public String tenantColumn();

    /**
     * 对应数据库的值
     * @return
     */
    public String tenantValue();
}
