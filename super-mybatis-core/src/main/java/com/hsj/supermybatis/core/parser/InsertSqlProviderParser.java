package com.hsj.supermybatis.core.parser;

import com.hsj.supermybatis.base.annotation.Column;
import com.hsj.supermybatis.base.annotation.PrimaryKey;
import com.hsj.supermybatis.base.enu.BaseSqlTemplate;
import com.hsj.supermybatis.base.enu.PrimaryKeyType;
import com.hsj.supermybatis.core.setting.GlobalConstants;
import com.hsj.supermybatis.core.tools.*;

import java.io.Serializable;
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
            if((item.getAnnotation(PrimaryKey.class)).keyType() != PrimaryKeyType.AUTO){
                columnBuffer.append(String.format(",`%s`", TableTools.fieldToColumn(setting, item)));
                valuesBuffer.append(String.format(",#{%s.%s}", insertEntity.getClass().getSimpleName(), item.getName()));

                Serializable id = null;

                PrimaryKeyType keyType = (item.getAnnotation(PrimaryKey.class)).keyType();

                if(keyType == null){
                    keyType = setting.getDatabaseSetting().getPrimaryKeyType();
                }

                if(keyType == PrimaryKeyType.UUID){
                    id = setting.getIdentifierGenerator(GlobalConstants.UUID_GENERATOR_KEY).nextId(insertEntity);
                }else if(keyType == PrimaryKeyType.SNOWFLAKE){
                    id = setting.getIdentifierGenerator(GlobalConstants.SNOWFLAKE_GENERATOR_KEY).nextId(insertEntity);
                }else{
                    SuperMybatisAssert.check(ReflectionUtil.invokeGetterMethod(insertEntity, item.getName()) !=  null, "The primary key has no assignment");
                    id = (Serializable)ReflectionUtil.invokeGetterMethod(insertEntity, item.getName());
                }
                ReflectionUtil.invokeSetterMethod(insertEntity, item.getName(), id);
                map.put(SqlProviderConstants.PRIMARY_KEY_VALUE, id);
            }
        });


        Arrays.asList(ReflectionUtil.getDeclaredField(insertEntity)).stream().
                filter(item -> item.getAnnotation(PrimaryKey.class) == null).forEach(item -> {
            Column c = item.getAnnotation(Column.class);
            if(c == null || !c.ignored()) {
                columnBuffer.append(String.format(",`%s`", TableTools.fieldToColumn(setting, item)));
                valuesBuffer.append(String.format(",#{%s.%s}", insertEntity.getClass().getSimpleName(), item.getName()));
            }
        });

        map.put(insertEntity.getClass().getSimpleName(), insertEntity);

        return String.format(BaseSqlTemplate.INSERT.getSql(), TABLE_NAME, columnBuffer.substring(1), valuesBuffer.substring(1));
    }
}
