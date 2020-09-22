package com.hsj.supermybatis.core.parser;

import com.hsj.supermybatis.base.annotation.PrimaryKey;
import com.hsj.supermybatis.base.enu.BaseSqlTemplate;
import com.hsj.supermybatis.base.enu.PrimaryKeyType;
import com.hsj.supermybatis.core.provider.SqlProviderConstants;
import com.hsj.supermybatis.core.setting.GlobalConstants;
import com.hsj.supermybatis.core.tools.CamelCaseUtils;
import com.hsj.supermybatis.core.tools.ReflectionUtil;
import com.hsj.supermybatis.core.tools.SqlPrint;
import com.hsj.supermybatis.core.tools.SuperMybatisAssert;

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

        boolean camelModel = setting.getDatabaseSetting().getCamelModel();

        Object insertEntity = map.get(SqlProviderConstants.ENTITY);

        Arrays.asList(insertEntity.getClass().getDeclaredFields()).stream().
                filter(item -> item.getAnnotation(PrimaryKey.class) != null).forEach(item -> {
            if((item.getAnnotation(PrimaryKey.class)).keyType() != PrimaryKeyType.AUTO){
                columnBuffer.append(String.format(",`%s`", (camelModel ? CamelCaseUtils.processNameWithUnderLine(item.getName()) : item.getName())));
                valuesBuffer.append(String.format(",#{%s.%s}", insertEntity.getClass().getSimpleName(), item.getName()));

                String id = null;

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
                }
                ReflectionUtil.invokeSetterMethod(insertEntity, item.getName(), id);
            }
        });


        Arrays.asList(insertEntity.getClass().getDeclaredFields()).stream().
                filter(item -> item.getAnnotation(PrimaryKey.class) == null).forEach(item -> {
            columnBuffer.append(String.format(",`%s`", (camelModel ? CamelCaseUtils.processNameWithUnderLine(item.getName()) : item.getName())));
            valuesBuffer.append(String.format(",#{%s.%s}", insertEntity.getClass().getSimpleName(), item.getName()));
        });

        map.put(insertEntity.getClass().getSimpleName(), insertEntity);

        String sql = String.format(BaseSqlTemplate.INSERT.getSql(), TABLE_NAME, columnBuffer.substring(1), valuesBuffer.substring(1));
        /**
         * 打印sql
         */
        SqlPrint.print(map, sql);
        return sql;
    }
}
