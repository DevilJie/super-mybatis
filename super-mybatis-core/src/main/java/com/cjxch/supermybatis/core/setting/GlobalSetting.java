package com.cjxch.supermybatis.core.setting;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.cjxch.supermybatis.base.bean.DataSourcesSetting;
import com.cjxch.supermybatis.base.exception.SuperMybatisException;
import com.cjxch.supermybatis.core.datasource.DataSourceConfig;
import com.cjxch.supermybatis.core.datasource.SuperMybatisDatasourceHandler;
import com.cjxch.supermybatis.core.datasource.SuperMybatisRouteDatasources;
import com.cjxch.supermybatis.core.generator.IdentifierGenerator;
import com.cjxch.supermybatis.core.generator.SnowflakeIdentifierGenerator;
import com.cjxch.supermybatis.core.generator.UuidIdentifierGenerator;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Super-Mybatis 全局设置项目
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/19/18:23
 */
public class GlobalSetting {

    /**
     * 主键策略，雪花算法相关配置
     */
    private SnowflakeSetting snowflakeSetting;
    /**
     * 主键生成器
     */
    private Map<String, IdentifierGenerator> identifierGeneratorMap = new HashMap<>();

    /**
     * 数据库配置
     */
    private DatabaseSetting databaseSetting = new DatabaseSetting();
//
//    /**
//     * 数据库缓存
//     */
//    private DbCacheSetting dbCacheSetting;

    private String driverClass;

//    private Boolean cacheSwitch = false;

    private String currentDataBase;

    private String defaultDataBase;
    /**
     * 开启debug模式
     * 输出Super-Mybatis debug日志
     */
    private Boolean debug = false;

    private AbstractRoutingDataSource dataSource;

    public AbstractRoutingDataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(AbstractRoutingDataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 获取主键生成器
     * @param key 生成器标识
     * @return
     */
    public IdentifierGenerator getIdentifierGenerator(String key) {
        if(identifierGeneratorMap.get(key) == null){
            IdentifierGenerator identifierGenerator = null;
            if(key.equals(GlobalConstants.SNOWFLAKE_GENERATOR_KEY)) {
                identifierGenerator = new SnowflakeIdentifierGenerator(snowflakeSetting.getWorkerId());
            }else if(key.equals(GlobalConstants.UUID_GENERATOR_KEY)){
                identifierGenerator = new UuidIdentifierGenerator();
            }
            identifierGeneratorMap.put(key, identifierGenerator);
        }

        if(identifierGeneratorMap.get(key) == null){
            throw new SuperMybatisException("unknown identifierGenerator key，please check ！");
        }

        return identifierGeneratorMap.get(key);
    }

    public SnowflakeSetting getSnowflakeSetting() {
        return snowflakeSetting;
    }

    public GlobalSetting setSnowflakeSetting(SnowflakeSetting snowflakeSetting) {
        this.snowflakeSetting = snowflakeSetting;
        return this;
    }

    public DatabaseSetting getDatabaseSetting() {
        return databaseSetting;
    }

    public GlobalSetting setDatabaseSetting(DatabaseSetting databaseSetting) {
        this.databaseSetting = databaseSetting;
        return this;
    }

    /**
     * 初始化一个全局设置
     * @return
     */
    public static GlobalSetting create(){
        return new GlobalSetting().setDatabaseSetting(new DatabaseSetting()).setSnowflakeSetting(new SnowflakeSetting());
    }

    /**
     * 缓存全局信息
     */
    private static GlobalSetting GLOBAL_SETTING = null;


    public static void setGlobalSetting(GlobalSetting globalConfig) {
        // 设置全局设置
        GLOBAL_SETTING = globalConfig;
    }

    public static GlobalSetting getGlobalSetting() {
        return GLOBAL_SETTING;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getDriverClass() {
        if(dataSource != null){
            Map<String, Object> res = ((SuperMybatisRouteDatasources)dataSource).getDatasource();
            String currentDataSource = SuperMybatisDatasourceHandler.getCurrentDataSource();
            if(StringUtils.isEmpty(currentDataSource)){
                for(Map.Entry<String, Object> entry : res.entrySet()){
                    if(entry.getKey().equals("pool")) continue;
                    DataSourcesSetting vv = JSONObject.parseObject(JSON.toJSONString(entry.getValue()), DataSourcesSetting.class);
                    return vv.getDriverClassName();
                }
            }else {
                for (Map.Entry<String, Object> entry : res.entrySet()) {
                    if(currentDataSource.equals(entry.getKey())){
                        DataSourcesSetting vv = JSONObject.parseObject(JSON.toJSONString(entry.getValue()), DataSourcesSetting.class);
                        return vv.getDriverClassName();
                    }
                }
            }
            throw new SuperMybatisException("Unknown Driver class");
        }else{
            return driverClass;
        }
    }

    public String getCurrentDataBase() {
        return currentDataBase;
    }

    public void setCurrentDataBase(String currentDataBase) {
        this.currentDataBase = currentDataBase;
    }

    public Boolean getDebug() {
        return debug;
    }

    public void setDebug(Boolean debug) {
        this.debug = debug;
    }

    public String getDefaultDataBase() {
        return defaultDataBase;
    }

    public void setDefaultDataBase(String defaultDataBase) {
        this.defaultDataBase = defaultDataBase;
    }
}
