package com.cjxch.supermybatis.core.parser;

import com.cjxch.supermybatis.base.annotation.Column;
import com.cjxch.supermybatis.base.annotation.PrimaryKey;
import com.cjxch.supermybatis.base.enu.BaseSqlTemplate;
import com.cjxch.supermybatis.base.enu.PrimaryKeyType;
import com.cjxch.supermybatis.core.generator.IdentifierGenerator;
import com.cjxch.supermybatis.core.setting.DatabaseSetting;
import com.cjxch.supermybatis.core.setting.GlobalConstants;
import com.cjxch.supermybatis.core.setting.GlobalSetting;
import com.cjxch.supermybatis.core.tools.ReflectionUtil;
import com.cjxch.supermybatis.core.tools.SuperMybatisAssert;
import com.cjxch.supermybatis.core.tools.TableTools;
import com.cjxch.supermybatis.tenant.SuperMybatisTenant;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/22/15:46
 */
public class InsertSqlProviderParser extends BaseSqlProviderParser {

    @Override
    public String execute(Map<String, Object> map) {
        /**
         * 公共初始化
         */
        commonInit(map);

        StringBuffer valuesBuffer = new StringBuffer();
        StringBuffer columnBuffer = new StringBuffer();

        Object insertEntity = map.get(SqlProviderConstants.ENTITY);

        Arrays.asList(ReflectionUtil.getDeclaredField(insertEntity)).stream().
                filter(item -> item.getAnnotation(PrimaryKey.class) != null).forEach(item -> {

            PrimaryKeyType keyType = (item.getAnnotation(PrimaryKey.class)).keyType();

            if(keyType == PrimaryKeyType.NONE){
                keyType = setting.getDatabaseSetting().getPrimaryKeyType();
            }

            if(keyType != PrimaryKeyType.AUTO){
                columnBuffer.append(String.format(",`%s`", TableTools.fieldToColumn(setting, item)));
                valuesBuffer.append(String.format(",#{%s.%s}", insertEntity.getClass().getSimpleName(), item.getName()));

                Serializable id = processPrimaryKey(insertEntity, item, keyType, setting.getIdentifierGenerator(GlobalConstants.UUID_GENERATOR_KEY), setting.getIdentifierGenerator(GlobalConstants.SNOWFLAKE_GENERATOR_KEY));
                map.put(SqlProviderConstants.PRIMARY_KEY_VALUE, id);
            }
            map.put(SqlProviderConstants.PRIMARY_KEY, item.getName());
        });


        Arrays.asList(ReflectionUtil.getDeclaredField(insertEntity)).stream().
                filter(item -> item.getAnnotation(PrimaryKey.class) == null
                        && !Modifier.isFinal(item.getModifiers())
                        && !Modifier.isStatic(item.getModifiers())).forEach(item -> {
            Column c = item.getAnnotation(Column.class);
            if(c == null || !c.ignored()) {
                columnBuffer.append(String.format(",`%s`", TableTools.fieldToColumn(setting, item)));
                valuesBuffer.append(String.format(",#{%s.%s}", insertEntity.getClass().getSimpleName(), item.getName()));
            }
        });

        if(GlobalSetting.getGlobalSetting().getDatabaseSetting().getTenant() && SuperMybatisTenant.currentTenant() != null){
            String column = SuperMybatisTenant.currentTenant().tenantColumn();
            String value = SuperMybatisTenant.currentTenant().tenantValue();
            String val = System.currentTimeMillis()+column;
            columnBuffer.append(String.format(",`%s`", column));
            valuesBuffer.append(String.format(",#{%s}", val));
            map.put(val, value);
        }

        map.put(insertEntity.getClass().getSimpleName(), insertEntity);

        return String.format(BaseSqlTemplate.INSERT.getSql(), TABLE_NAME, columnBuffer.substring(1), valuesBuffer.substring(1));
    }

    static Serializable processPrimaryKey(Object insertEntity, Field item, PrimaryKeyType keyType, IdentifierGenerator uuididentifierGenerator, IdentifierGenerator snowidentifierGenerator) {
        Serializable id = null;

        if(keyType == PrimaryKeyType.UUID){
            id = uuididentifierGenerator.nextId(insertEntity);
        }else if(keyType == PrimaryKeyType.SNOWFLAKE){
            id = snowidentifierGenerator.nextId(insertEntity);
        }else{
            SuperMybatisAssert.check(ReflectionUtil.invokeGetterMethod(insertEntity, item.getName()) !=  null, "The primary key has no assignment");
            id = (Serializable)ReflectionUtil.invokeGetterMethod(insertEntity, item.getName());
        }
        ReflectionUtil.invokeSetterMethod(insertEntity, item.getName(), id);
        return id;
    }
}
