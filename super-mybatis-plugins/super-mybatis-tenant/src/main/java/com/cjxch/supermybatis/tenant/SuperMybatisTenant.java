package com.cjxch.supermybatis.tenant;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 多租户
 */
public class SuperMybatisTenant implements AutoCloseable {

    public static Map<Long, SuperMybatisTenantInfo> tenantInfos = new ConcurrentHashMap<>();

    public static SuperMybatisTenant openTenant(String tenantColumn, String tenantValue){
        SuperMybatisTenantInfo info = new SuperMybatisTenantInfo() {
            @Override
            public String tenantColumn() {
                return tenantColumn;
            }

            @Override
            public String tenantValue() {
                return tenantValue;
            }
        };
        tenantInfos.put(Thread.currentThread().getId(), info);
        return new SuperMybatisTenant(Thread.currentThread().getId());
    }

    public static SuperMybatisTenantInfo currentTenant(){
        return tenantInfos.get(Thread.currentThread().getId());
    }

    private long id;

    private SuperMybatisTenantInfo tenantInfo;

    private SuperMybatisTenant(long id){
        this.id = id;
    }

    @Override
    public void close() {
        tenantInfos.remove(id);
    }
}
