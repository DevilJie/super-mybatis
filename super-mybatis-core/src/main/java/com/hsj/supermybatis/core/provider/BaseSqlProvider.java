package com.hsj.supermybatis.core.provider;

import com.hsj.supermybatis.base.annotation.PrimaryKey;
import com.hsj.supermybatis.base.annotation.Table;
import com.hsj.supermybatis.core.setting.GlobalConstants;
import com.hsj.supermybatis.core.setting.GlobalSetting;
import com.hsj.supermybatis.core.tools.CamelCaseUtils;
import com.hsj.supermybatis.core.tools.SuperMybatisAssert;
import org.apache.ibatis.mapping.SqlCommandType;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/21/7:29
 */
public abstract class BaseSqlProvider {

    public BaseSqlProvider(){}

    /**
     * 拼接SQL的表名称
     */
    protected String TABLE_NAME;

    /**
     * 拼接SQL的表别名
     */
    protected String TABLE_NAME_ALIAS;

    /**
     * 拼接联查SQL的所有表名称
     */
    protected String[] JOIN_TABLE_NAME;

    /**
     * 拼接联查SQL的所有表别名
     */
    protected String[] JOIN_TABLE_NAME_ALIAS;

    /**
     * 使用mybatis内置的Sql类型标识
     */
    protected SqlCommandType SQL_TYPE;

    /**
     * 主键列名
     */
    protected String PRIMARY_KEY;


    public String execute(Map<String, Object> map){
        if(SQL_TYPE.equals(SqlCommandType.INSERT)){
            insertSqlInit(map);
        }else if(SQL_TYPE.equals(SqlCommandType.SELECT)){
            selectSqlInit(map);
        }
        return null;
    }

    private void selectSqlInit(Map<String, Object> map){
        /**
         * 如果是按照主键查询
         */
        if(this instanceof GetSqlProvider){
            String className = ((Class)map.get(SqlProviderConstants.CLASS_NAME)).getName();
            SuperMybatisAssert.check((className != null && !"".equals(className)), "className can't be null");
            Object entity = null;
            try{
                entity = Class.forName(className).newInstance();
            }catch(Exception e){
                e.printStackTrace();
                entity = null;
            }

            SuperMybatisAssert.check(entity != null, "unknown className");

            Table table = (Table)entity.getClass().getAnnotation(Table.class);
            TABLE_NAME = entity.getClass().getSimpleName();

            //TODO 全局变量配置，需要动态读取
            GlobalSetting setting = GlobalSetting.getGlobalSetting();
            TABLE_NAME = setting.getDatabaseSetting().getCamelModel() ? CamelCaseUtils.processNameWithUnderLine(TABLE_NAME) : TABLE_NAME;
            /**
             * 如果配置了全局表前缀，则需要拼接
             */
            TABLE_NAME = (!StringUtils.isEmpty(setting.getDatabaseSetting().getTablePerfix()) ? setting.getDatabaseSetting().getTablePerfix() : "") + TABLE_NAME;

            /**
             * 如果配置了 @Table 注解，并且设置了表名称，则优先使用
             */
            if(table != null && !"".equals(table.value())) TABLE_NAME = table.value();


            List<Field> fieldList = Arrays.asList(entity.getClass().getDeclaredFields()).stream().
                    filter(item -> item.getAnnotation(PrimaryKey.class) != null).collect(Collectors.toList());

            Field field = (fieldList != null && fieldList.size() > 0) ? fieldList.get(0) : null;

            SuperMybatisAssert.check(field != null, "Sorry ! Primary key not found , please check");

            PrimaryKey pk = field.getAnnotation(PrimaryKey.class);

            PRIMARY_KEY = field.getName();
            PRIMARY_KEY = setting.getDatabaseSetting().getCamelModel() ? CamelCaseUtils.processNameWithUnderLine(PRIMARY_KEY) : PRIMARY_KEY;
            if(pk != null && !"".equals(pk.key())) PRIMARY_KEY = pk.key();
        }
    }

    private void insertSqlInit(Map<String, Object> map) {
        String className = (String)map.get(SqlProviderConstants.CLASS_NAME);

        SuperMybatisAssert.check((className == null || "".equals(className)), "className can't be null");

        /**
         * 获取实体类
         */
        Object entity = map.get(SqlProviderConstants.ENTITY);

        GlobalSetting setting = GlobalSetting.getGlobalSetting();

        /**
         * 查看当前实体类是否配置了Table注解
         * 获取表名称
         */
        Table table = (Table)entity.getClass().getAnnotation(Table.class);
        TABLE_NAME = entity.getClass().getSimpleName();
        TABLE_NAME = setting.getDatabaseSetting().getCamelModel() ? CamelCaseUtils.processNameWithUnderLine(TABLE_NAME) : TABLE_NAME;
        if(table != null && !"".equals(TABLE_NAME)) TABLE_NAME = table.value();

        TABLE_NAME_ALIAS = TABLE_NAME.toLowerCase();

        Method[] methods = entity.getClass().getMethods();



    }
}
