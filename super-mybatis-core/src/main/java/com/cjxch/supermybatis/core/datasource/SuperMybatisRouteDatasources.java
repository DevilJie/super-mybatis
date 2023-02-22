package com.cjxch.supermybatis.core.datasource;

import com.cjxch.supermybatis.base.bean.DataSourcesSetting;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.Map;


public class SuperMybatisRouteDatasources extends AbstractRoutingDataSource {

    private Map<String, Object> datasource;

    private boolean active;
    @Override
    protected Object determineCurrentLookupKey() {
        return SuperMybatisDatasourceHandler.getCurrentDataSource();
    }

    @Override
    protected DataSource determineTargetDataSource() {
        return super.determineTargetDataSource();
    }

    public Map<String, Object> getDatasource() {
        return datasource;
    }

    public void setDatasource(Map<String, Object> datasource) {
        this.datasource = datasource;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
