package com.cjxch.supermybatis.core.parser;

import com.cjxch.supermybatis.base.annotation.Column;
import com.cjxch.supermybatis.base.annotation.PrimaryKey;
import com.cjxch.supermybatis.base.enu.BaseSqlTemplate;
import com.cjxch.supermybatis.base.enu.PrimaryKeyType;
import com.cjxch.supermybatis.core.setting.GlobalConstants;
import com.cjxch.supermybatis.core.tools.ReflectionUtil;
import com.cjxch.supermybatis.core.tools.SuperMybatisAssert;
import com.cjxch.supermybatis.core.tools.TableTools;

import java.io.Serializable;
import java.lang.reflect.Modifier;
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

            Arrays.asList(ReflectionUtil.getDeclaredField(insertEntity)).stream().
                    filter(item -> item.getAnnotation(PrimaryKey.class) != null).forEach(item -> {
                PrimaryKeyType keyType = (item.getAnnotation(PrimaryKey.class)).keyType();

                if(keyType == PrimaryKeyType.NONE){
                    keyType = setting.getDatabaseSetting().getPrimaryKeyType();
                }

                if(keyType != PrimaryKeyType.AUTO){
                    if(index.get() == 0)
                        columnBuffer.append(String.format(",`%s`", TableTools.fieldToColumn(setting, item)));
                    valuesBuffer.append(String.format(",#{%s[%s].%s}", SqlProviderConstants.ENTITY_LIST, index.get(), item.getName()));
                    Serializable id = InsertSqlProviderParser.processPrimaryKey(insertEntity, item, keyType, setting.getIdentifierGenerator(GlobalConstants.UUID_GENERATOR_KEY), setting.getIdentifierGenerator(GlobalConstants.SNOWFLAKE_GENERATOR_KEY));
                    primaryKeyIds.add(id);
                }
            });


            Arrays.asList(ReflectionUtil.getDeclaredField(insertEntity)).stream().
                    filter(item -> item.getAnnotation(PrimaryKey.class) == null
                            && !Modifier.isFinal(item.getModifiers())
                            && !Modifier.isStatic(item.getModifiers())).forEach(item -> {
                Column c = item.getAnnotation(Column.class);
                if(c == null || !c.ignored()) {
                    if (index.get() == 0)
                        columnBuffer.append(String.format(",`%s`", TableTools.fieldToColumn(setting, item)));
                    valuesBuffer.append(String.format(",#{%s[%s].%s}", SqlProviderConstants.ENTITY_LIST, index.get(), item.getName()));
                }
            });

            valuesBufferTotal.append(",("+valuesBuffer.substring(1)+")\n");

            index.addAndGet(1);
        });

        map.put(SqlProviderConstants.PRIMARY_KEY_VALUE, primaryKeyIds.toArray(new Serializable[primaryKeyIds.size()]));

        return String.format(BaseSqlTemplate.BATCH_INSERT.getSql(), TABLE_NAME, columnBuffer.substring(1), valuesBufferTotal.substring(1));
    }
}
