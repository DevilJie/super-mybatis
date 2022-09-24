package com.cjxch.supermybatis.core.datasource;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.cjxch.supermybatis.base.bean.DataSourcesSetting;
import com.cjxch.supermybatis.core.setting.GlobalConstants;
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
        final Class<?>[] type = {null};
        if(datasource != null && datasource.size() > 0){
            datasource.forEach((k,v) -> {
                if(k.equals("pool")){
                    JSONObject map = JSON.parseObject(JSON.toJSONString(v));
                    try {
                        type[0] = Class.forName(map.getString("type"));
                        Object obj = Class.forName(map.getString("type")).newInstance();
                        Refl
                    } catch (ClassNotFoundException e) {
                    } catch (InstantiationException e) {
                    } catch (IllegalAccessException e) {
                    }
                }else{
                    DataSourcesSetting vv = JSONObject.parseObject(JSON.toJSONString(v), DataSourcesSetting.class);
                    DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
                    dataSourceBuilder.driverClassName(vv.getDriverClassName());
                    dataSourceBuilder.password(vv.getPassword());
                    dataSourceBuilder.username(vv.getUserName());
                    dataSourceBuilder.url(vv.getUrl());
                    if(type[0] != null) dataSourceBuilder.type(type[0]);
                    ret.put(k,dataSourceBuilder.build());
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
