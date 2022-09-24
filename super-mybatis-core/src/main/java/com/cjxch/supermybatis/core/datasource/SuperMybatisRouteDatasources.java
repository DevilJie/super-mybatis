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

    @Override
    protected Object determineCurrentLookupKey() {
        return SuperMybatisDatasourceHandler.getCurrentDataSource();
    }

    @Override
    protected DataSource determineTargetDataSource() {
        return super.determineTargetDataSource();
    }
}
