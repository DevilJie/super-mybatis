package com.hsj.supermybatis.core.parser;

import com.hsj.supermybatis.base.annotation.PrimaryKey;
import com.hsj.supermybatis.base.enu.BaseSqlTemplate;
import com.hsj.supermybatis.base.enu.PrimaryKeyType;
import com.hsj.supermybatis.core.setting.GlobalConstants;
import com.hsj.supermybatis.core.tools.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: 菜鸡小彩虹
 * @Date: 2020/09/22/15:46
 */
public class BatchInsertSqlProviderParser extends BaseSqlProviderParser {

    @Override
    public String execute(Map<String, Object> map) {
        /**
         * 公共初始化
         */
        commonInit(map);

        StringBuffer valuesBufferTotal = new StringBuffer();
        StringBuffer columnBuffer = new StringBuffer();
        List primaryKeyIds = new ArrayList();

        AtomicInteger index = new AtomicInteger(0);
        ((List<Object>) map.get(SqlProviderConstants.ENTITY_LIST)).stream().forEach(insertEntity -> {

            StringBuffer valuesBuffer = new StringBuffer();

            Arrays.asList(insertEntity.getClass().getDeclaredFields()).stream().
                    filter(item -> item.getAnnotation(PrimaryKey.class) != null).forEach(item -> {

                if((item.getAnnotation(PrimaryKey.class)).keyType() != PrimaryKeyType.AUTO){
                    if(index.get() == 0)
                        columnBuffer.append(String.format(",`%s`", TableTools.fieldToColumn(setting, item)));
                    valuesBuffer.append(String.format(",#{%s[%s].%s}", SqlProviderConstants.ENTITY_LIST, index.get(), item.getName()));

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
                    primaryKeyIds.add(id);
                }
            });


            Arrays.asList(insertEntity.getClass().getDeclaredFields()).stream().
                    filter(item -> item.getAnnotation(PrimaryKey.class) == null).forEach(item -> {
                if(index.get() == 0)
                    columnBuffer.append(String.format(",`%s`", TableTools.fieldToColumn(setting, item)));
                valuesBuffer.append(String.format(",#{%s[%s].%s}", SqlProviderConstants.ENTITY_LIST, index.get(), item.getName()));
            });

            valuesBufferTotal.append(",("+valuesBuffer.substring(1)+")\n");

            index.addAndGet(1);
        });


        String sql = String.format(BaseSqlTemplate.BATCH_INSERT.getSql(), TABLE_NAME, columnBuffer.substring(1), valuesBufferTotal.substring(1));
        /**
         * 打印sql
         */
        SqlPrint.print(map, sql);
        map.put(SqlProviderConstants.PRIMARY_KEY_VALUE, primaryKeyIds.toArray(new String[primaryKeyIds.size()]));
        return sql;
    }
}
