package com.cjxch.supermybatis.tenant;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 多租户
 */
public class SuperMybatisTenant implements AutoCloseable {

    private static ThreadLocal<SuperMybatisTenantInfo> tenantInfos = new ThreadLocal<SuperMybatisTenantInfo>();

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
        tenantInfos.set(info);
        return new SuperMybatisTenant();
    }

    public static SuperMybatisTenantInfo currentTenant(){
        return tenantInfos.get();
    }

    @Override
    public void close() {
        tenantInfos.remove();
    }
}
