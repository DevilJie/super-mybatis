package com.cjxch.supermybatis.core.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.cjxch.supermybatis.base.bean.DataSourcesSetting;
import com.cjxch.supermybatis.core.datasource.druid.DruidProperties;
import com.cjxch.supermybatis.core.setting.GlobalConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = GlobalConstants.SUPER_MYBATIS)
class DataSourceConfig {

    private Map<String, Object> datasource;

    @Autowired
    private DruidProperties druidProperties;
    @Bean(name="supermybatisDataSource")
    public DataSource supermybatisDataSource(){
        SuperMybatisRouteDatasources ds = new SuperMybatisRouteDatasources();
        Map<Object, Object> dsMap = processDatasources();
        ds.setTargetDataSources(dsMap);
        ds.setDefaultTargetDataSource(dsMap.values().toArray()[0]);
        ds.afterPropertiesSet();
        return ds;
    }

    private Map<Object, Object> processDatasources(){
        Map<Object, Object> ret = new LinkedHashMap<>();
        if(datasource != null && datasource.size() > 0){
            datasource.forEach((k,v) -> {
                if(!k.equals("pool")){
                    DruidDataSource ds = new DruidDataSource();
                    DataSourcesSetting vv = JSONObject.parseObject(JSON.toJSONString(v), DataSourcesSetting.class);
                    ds.setDriverClassName(vv.getDriverClassName());
                    ds.setUsername(vv.getUsername());
                    ds.setPassword(vv.getPassword());
                    ds.setUrl(vv.getUrl());
                    druidProperties.dataSource(ds);
                    ret.put(k, ds);
                }
            });
        }
        return ret;
    }

    public Map<String, Object> getDatasource() {
        return datasource;
    }

    public void setDatasource(Map<String, Object> datasource) {
        this.datasource = datasource;
    }
}
